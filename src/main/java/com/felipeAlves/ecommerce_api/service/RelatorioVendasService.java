package com.felipeAlves.ecommerce_api.service;

import com.felipeAlves.ecommerce_api.model.RelatorioVendas;
import com.felipeAlves.ecommerce_api.repository.RelatorioVendasRepository;
import com.felipeAlves.ecommerce_api.specification.RelatorioVendasSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class RelatorioVendasService {

    @Autowired
    private RelatorioVendasRepository relatorioVendasRepository;

    public List<RelatorioVendas> buscarRelatorios(
    		Long idProduto,
    		String status,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim,
            Integer quantidadeVendidaMin,
            BigDecimal faturamentoMin,
            String nomeProduto
    ) {
        Specification<RelatorioVendas> spec = Specification.where(null);

        if (inicio != null) {
            spec = spec.and(RelatorioVendasSpecifications.periodoMaiorQue(inicio));
        }

        if (fim != null) {
            spec = spec.and(RelatorioVendasSpecifications.periodoMenorQue(fim));
        }

        if (status != null) {
            spec = spec.and(RelatorioVendasSpecifications.statusIgual(status));
        }

        if (faturamentoMin != null) {
            spec = spec.and(RelatorioVendasSpecifications.faturamentoMaiorQue(faturamentoMin));
        }

        if (nomeProduto != null) {
            spec = spec.and(RelatorioVendasSpecifications.produtoNomeContem(nomeProduto));
        }

        return relatorioVendasRepository.findAll(spec);
    }
}
