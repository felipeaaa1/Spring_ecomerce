package com.felipeAlves.ecommerce_api.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.felipeAlves.ecommerce_api.model.ItemCarrinho;
import com.felipeAlves.ecommerce_api.model.ItemPedido;
import com.felipeAlves.ecommerce_api.model.Pedido;
import com.felipeAlves.ecommerce_api.model.Produto;
import com.felipeAlves.ecommerce_api.model.Usuario;
import com.felipeAlves.ecommerce_api.model.Utils.StatusPedido;
import com.felipeAlves.ecommerce_api.repository.ClienteRepository;
import com.felipeAlves.ecommerce_api.repository.ItemPedidoRepository;
import com.felipeAlves.ecommerce_api.repository.ProdutoRepository;

@Service
public class CarrinhoService {

    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private PedidoService pedidoService;
    
    @Autowired
    ItemPedidoRepository itemPedidoRepository;

    private final Map<Long, List<ItemCarrinho>> carrinhos = new HashMap<>();

    public void adicionarItem(ItemCarrinho itemCarrinho) {
        Long usuarioId = getUsuarioAutenticado().getIdUsuario();
        Produto produto = produtoRepository.findById(itemCarrinho.getProduto().getIdProduto())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));

        if (produto.getQuantidadeEmEstoque() < itemCarrinho.getQuantidade()) {
            throw new IllegalArgumentException("Estoque insuficiente para o produto: " + produto.getNomeProduto());
        }

        carrinhos.putIfAbsent(usuarioId, new ArrayList<>());
        List<ItemCarrinho> itens = carrinhos.get(usuarioId);

        boolean itemAdicionado = false;
        for (ItemCarrinho item : itens) {
            if (item.getProduto().getIdProduto().equals(itemCarrinho.getProduto().getIdProduto())) {
                int novaQuantidade = item.getQuantidade() + itemCarrinho.getQuantidade();
                if (produto.getQuantidadeEmEstoque() < novaQuantidade) {
                    throw new IllegalArgumentException("Estoque insuficiente para o produto: " + produto.getNomeProduto());
                }
                item.setQuantidade(novaQuantidade);
                itemAdicionado = true;
                break;
            }
        }

        if (!itemAdicionado) {
            itemCarrinho.setProduto(produto);
            itens.add(itemCarrinho);
        }
    }


    public void removerItem(ItemCarrinho itemCarrinho) {
        Long usuarioId = getUsuarioAutenticado().getIdUsuario();
        List<ItemCarrinho> itens = carrinhos.get(usuarioId);

        if (itens != null) {
            itens.removeIf(item -> {
                if (item.getProduto().getIdProduto().equals(itemCarrinho.getProduto().getIdProduto())) {
                    int novaQuantidade = item.getQuantidade() - itemCarrinho.getQuantidade();
                    if (novaQuantidade <= 0) {
                        return true; 
                    } else {
                        item.setQuantidade(novaQuantidade); 
                    }
                }
                return false;
            });
        }
    }


    public List<ItemCarrinho> listarItens() {
        Long usuarioId = getUsuarioAutenticado().getIdUsuario();
        return carrinhos.getOrDefault(usuarioId, new ArrayList<>());
    }

    public BigDecimal calcularTotal() {
        Long usuarioId = getUsuarioAutenticado().getIdUsuario();
        List<ItemCarrinho> itens = carrinhos.getOrDefault(usuarioId, new ArrayList<>());

        return itens.stream()
                .map(item -> item.getProduto().getPreco().multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void finalizarCompra() {
        Long usuarioId = getUsuarioAutenticado().getIdUsuario();
        List<ItemCarrinho> itens = carrinhos.remove(usuarioId);

        if (itens == null || itens.isEmpty()) {
            throw new IllegalArgumentException("Carrinho vazio.");
        }

        // Criar um novo pedido
        Pedido pedidoNovo = new Pedido();
        pedidoNovo.setCliente(clienteRepository.findByUsuarioIdUsuario(usuarioId));
        pedidoNovo.setDataPedido(LocalDateTime.now());
        pedidoNovo.setStatus(StatusPedido.EM_PREPARACAO);
        pedidoNovo.setTotal(this.calcularTotal());

        pedidoNovo = pedidoService.salvarPedido(pedidoNovo, getUsuarioAutenticado());

        for (ItemCarrinho item : itens) {

                Produto produto = produtoRepository.findById(item.getProduto().getIdProduto())
                        .orElseThrow(() -> {
                            this.removerItem(item);
                            return new IllegalArgumentException("Produto não encontrado.");
                        });

                if (produto.getQuantidadeEmEstoque() < item.getQuantidade()) {
                    item.setQuantidade(produto.getQuantidadeEmEstoque());
                    String erro = "Estoque insuficiente para o produto: " + produto.getNomeProduto()+"\n atualmente temos "+produto.getQuantidadeEmEstoque().toString()+"em estoque";
                    throw new IllegalArgumentException(erro);
                }


            ItemPedido novoItemPedido = ItemPedido.builder()
                    .pedido(pedidoNovo)
                    .produto(produto)
                    .quantidade(item.getQuantidade())
                    .precoPorUnidade(produto.getPreco())
                    .subtotal(produto.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade())))
                    .usuarioCriacao(usuarioId)
                    .usuarioAtualizacao(usuarioId)
                    .build();

            itemPedidoRepository.save(novoItemPedido);
            
            pedidoNovo.setTotal(pedidoNovo.getTotal().add(novoItemPedido.getSubtotal()));
            pedidoNovo = pedidoService.atualizarPedido(pedidoNovo.getIdPedido(), pedidoNovo, getUsuarioAutenticado());

            // Atualizar o estoque do produto
            produto.setQuantidadeEmEstoque(produto.getQuantidadeEmEstoque() - item.getQuantidade());
            produtoRepository.save(produto);
        }
    }

    private Usuario getUsuarioAutenticado() {
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return usuarioLogado;
    }
    


}
