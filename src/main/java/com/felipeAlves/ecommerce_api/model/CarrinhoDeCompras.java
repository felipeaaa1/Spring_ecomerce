package com.felipeAlves.ecommerce_api.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;

@Getter
public class CarrinhoDeCompras {

    private List<ItemCarrinho> itens;

    public CarrinhoDeCompras() {
        this.itens = new ArrayList<>();
    }

    // Adicionar produto ao carrinho
    public void adicionarItem(Produto produto, int quantidade) {
        if (produto.getQuantidadeEmEstoque() < quantidade) {
            throw new IllegalArgumentException("Quantidade solicitada excede o estoque disponível.");
        }

        Optional<ItemCarrinho> itemExistente = itens.stream()
                .filter(item -> item.getProduto().getIdProduto().equals(produto.getIdProduto()))
                .findFirst();

        if (itemExistente.isPresent()) {
            ItemCarrinho item = itemExistente.get();
            item.setQuantidade(item.getQuantidade() + quantidade);
        } else {
            ItemCarrinho novoItem = new ItemCarrinho(produto, quantidade);
            itens.add(novoItem);
        }
    }

    // Remover produto do carrinho
    public void removerItem(Long idProduto) {
        itens.removeIf(item -> item.getProduto().getIdProduto().equals(idProduto));
    }

    // Atualizar quantidade de um item
    public void atualizarQuantidade(Long idProduto, int novaQuantidade) {
        Optional<ItemCarrinho> itemExistente = itens.stream()
                .filter(item -> item.getProduto().getIdProduto().equals(idProduto))
                .findFirst();

        if (itemExistente.isPresent()) {
            ItemCarrinho item = itemExistente.get();
            if (item.getProduto().getQuantidadeEmEstoque() < novaQuantidade) {
                throw new IllegalArgumentException("Quantidade solicitada excede o estoque disponível.");
            }
            item.setQuantidade(novaQuantidade);
        } else {
            throw new IllegalArgumentException("Produto não encontrado no carrinho.");
        }
    }

    // Calcular o total do carrinho
    public BigDecimal calcularTotal() {
        return itens.stream()
                .map(item -> item.getProduto().getPreco().multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Limpar o carrinho
    public void limparCarrinho() {
        itens.clear();
    }
}
