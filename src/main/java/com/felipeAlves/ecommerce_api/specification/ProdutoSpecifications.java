package com.felipeAlves.ecommerce_api.specification;

import org.springframework.data.jpa.domain.Specification;

import com.felipeAlves.ecommerce_api.model.Produto;

public class ProdutoSpecifications {

    public static Specification<Produto> nomeContem(String nome) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.like(root.get("nomeProduto"), "%" + nome + "%");
    }

    public static Specification<Produto> precoMaiorQue(Double preco) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.greaterThan(root.get("preco"), preco);
    }

    public static Specification<Produto> estoqueDisponivel() {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.greaterThan(root.get("quantidadeEmEstoque"), 0);
    }
}
