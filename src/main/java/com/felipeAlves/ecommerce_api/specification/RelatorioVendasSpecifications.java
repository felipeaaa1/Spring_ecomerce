package com.felipeAlves.ecommerce_api.specification;

import org.springframework.data.jpa.domain.Specification;

import com.felipeAlves.ecommerce_api.model.RelatorioVendas;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RelatorioVendasSpecifications {

    public static Specification<RelatorioVendas> periodoMaiorQue(LocalDate inicio) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("periodo"), inicio);
    }


    public static Specification<RelatorioVendas> periodoMenorQue(LocalDate fim) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("periodo"), fim);
    }

    public static Specification<RelatorioVendas> statusIgual(String status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<RelatorioVendas> faturamentoMaiorQue(BigDecimal valor) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("faturamentoPorItem"), valor);
    }

    public static Specification<RelatorioVendas> produtoNomeContem(String nomeProduto) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("nomeProduto"), "%" + nomeProduto + "%");
    }
}
