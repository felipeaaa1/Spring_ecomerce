package com.felipeAlves.ecommerce_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.felipeAlves.ecommerce_api.dto.PagamentoRequestDTO;
import com.felipeAlves.ecommerce_api.dto.PagamentoResponseDTO;
import com.felipeAlves.ecommerce_api.model.ItemCarrinho;
import com.felipeAlves.ecommerce_api.model.ItemPedido;
import com.felipeAlves.ecommerce_api.model.Pedido;
import com.felipeAlves.ecommerce_api.model.Produto;
import com.felipeAlves.ecommerce_api.model.Usuario;
import com.felipeAlves.ecommerce_api.model.Utils.StatusPedido;
import com.felipeAlves.ecommerce_api.repository.ItemPedidoRepository;
import com.felipeAlves.ecommerce_api.repository.PedidoRepository;
import com.felipeAlves.ecommerce_api.repository.ProdutoRepository;

@Service
public class PagamentoService {
	
	@Autowired
	ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    ProdutoRepository produtoRepository;
    
    @Autowired
    PedidoService pedidoService;

    public PagamentoResponseDTO processarPagamento(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));

        RestTemplate restTemplate = new RestTemplate();
        String pagamentoServiceUrl = "http://localhost:5000/api/pagamento";
        PagamentoRequestDTO pagamentoRequest = new PagamentoRequestDTO(pedidoId, pedido.getTotal());

        try {
            HttpEntity<PagamentoRequestDTO> requestEntity = new HttpEntity<>(pagamentoRequest);
            ResponseEntity<PagamentoResponseDTO> responseEntity = restTemplate.postForEntity(
                    pagamentoServiceUrl, requestEntity, PagamentoResponseDTO.class);

            return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Erro ao processar pagamento: " + e.getMessage());
        }
    }

	public String pagamentoAvulso(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
        if (!pedido.getStatus().getStatus().contains("PAGAMENTO_EFETUADO")) {
        
        RestTemplate restTemplate = new RestTemplate();
        String pagamentoServiceUrl = "http://localhost:5000/api/pagamento";
        PagamentoRequestDTO pagamentoRequest = new PagamentoRequestDTO(pedidoId, pedido.getTotal());
        List<ItemPedido> itens = itemPedidoRepository.findByPedidoIdPedido(pedidoId);

        try {
            HttpEntity<PagamentoRequestDTO> requestEntity = new HttpEntity<>(pagamentoRequest);
            ResponseEntity<PagamentoResponseDTO> responseEntity = restTemplate.postForEntity(
                    pagamentoServiceUrl, requestEntity, PagamentoResponseDTO.class);
            
            if (responseEntity.getBody().status().contains("Pagamento Efetuado")) {
                
                for (ItemPedido item : itens) {
                    Produto produto = produtoRepository.findById(item.getProduto().getIdProduto())
                            .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));

                    produto.setQuantidadeEmEstoque(produto.getQuantidadeEmEstoque() - item.getQuantidade());
                    produtoRepository.save(produto);
                }

                pedido.setStatus(StatusPedido.PAGAMENTO_EFETUADO);
            } else {
            	pedido.setStatus(StatusPedido.PAGAMENTO_NEGADO);
            }

            pedido = pedidoService.atualizarPedido(pedido.getIdPedido(), pedido, getUsuarioAutenticado());
            return null;


        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Erro ao processar pagamento: " + e.getMessage());
        }
    	
        }else 
        	return "Pedido pago anteriormente";

	}
	
    private Usuario getUsuarioAutenticado() {
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return usuarioLogado;
    }

}
