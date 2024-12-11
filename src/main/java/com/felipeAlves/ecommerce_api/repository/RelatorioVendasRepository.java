package com.felipeAlves.ecommerce_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.felipeAlves.ecommerce_api.model.RelatorioVendas;

@Repository
public interface RelatorioVendasRepository extends JpaRepository<RelatorioVendas, Long>, JpaSpecificationExecutor<RelatorioVendas> {
}
