package com.felipeAlves.ecommerce_api.controller;

import com.felipeAlves.ecommerce_api.model.RelatorioVendas;
import com.felipeAlves.ecommerce_api.service.RelatorioVendasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/relatorios")
public class RelatorioVendasController {

    @Autowired
    private RelatorioVendasService relatorioVendasService;

    @GetMapping
    public ResponseEntity<List<RelatorioVendas>> buscarRelatorios(@RequestParam(value = "idProduto", required = false) Long idProduto,
                                                                  @RequestParam(value = "status", required = false) String status,
                                                                  @RequestParam(value = "inicio", required = false) LocalDate inicio,
                                                                  @RequestParam(value = "fim", required = false) LocalDate fim,
                                                                  @RequestParam(value = "quantidadeVendidaMin", required = false) Integer quantidadeVendidaMin,
                                                                  @RequestParam(value = "faturamentoMin", required = false) BigDecimal faturamentoMin,
                                                                  @RequestParam(value = "nomeProduto", required = false) String nomeProduto
) {
        List<RelatorioVendas> relatorios = relatorioVendasService.buscarRelatorios(idProduto, status, inicio, fim, quantidadeVendidaMin, faturamentoMin, nomeProduto);
        return ResponseEntity.ok(relatorios);
    }
}
