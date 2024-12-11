package com.felipeAlves.ecommerce_api.controller;

import com.felipeAlves.ecommerce_api.model.RelatorioVendas;
import com.felipeAlves.ecommerce_api.service.RelatorioVendasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.PrintWriter;
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
                                                                  @RequestParam(value = "nomeProduto", required = false) String nomeProduto) {
        List<RelatorioVendas> relatorios = relatorioVendasService.buscarRelatorios(idProduto, status, inicio, fim, quantidadeVendidaMin, faturamentoMin, nomeProduto);
        return ResponseEntity.ok(relatorios);
    }

    @PostMapping("/gerarcsv")
    public ResponseEntity<?> gerarCsvRelatorio(@RequestParam(value = "idProduto", required = false) Long idProduto,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "inicio", required = false) LocalDate inicio,
            @RequestParam(value = "fim", required = false) LocalDate fim,
            @RequestParam(value = "quantidadeVendidaMin", required = false) Integer quantidadeVendidaMin,
            @RequestParam(value = "faturamentoMin", required = false) BigDecimal faturamentoMin,
            @RequestParam(value = "nomeProduto", required = false) String nomeProduto) {
    		try {
    		List<RelatorioVendas> relatorios = relatorioVendasService.buscarRelatorios(idProduto, status, inicio, fim, quantidadeVendidaMin, faturamentoMin, nomeProduto);
            String filePath = "relatorio_vendas.csv";
            try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
                // Escrevendo os filtros no início do arquivo
                writer.println("Parâmetros:");
                if (idProduto != null) writer.printf("idProduto: %s%n", idProduto.toString());
                if (status != null) writer.printf("status: %s%n", status);
                if (inicio != null) writer.printf("inicio: %s%n", inicio.toString());
                if (fim != null) writer.printf("fim: %s%n", fim.toString());
                if (quantidadeVendidaMin != null) writer.printf("quantidadeVendidaMin: %s%n", quantidadeVendidaMin.toString());
                if (faturamentoMin != null) writer.printf("faturamentoMin: %s%n", faturamentoMin.toString());
                if (nomeProduto != null) writer.printf("nomeProduto: %s%n", nomeProduto);

                // Cabeçalho do CSV
                writer.println();
                writer.println("ID Pedido,Período,Total Venda,Produtos Vendidos,Status,ID Produto,Nome Produto,Quantidade Vendida,Faturamento Por Item");

                // Dados do relatório
                for (RelatorioVendas relatorio : relatorios) {
                    writer.printf("%d,%s,%s,%d,%s,%d,%s,%d,%s%n",
                            relatorio.getIdPedido(),
                            relatorio.getPeriodo(),
                            relatorio.getTotalVenda(),
                            relatorio.getProdutosVendidos(),
                            relatorio.getStatus(),
                            relatorio.getIdProduto(),
                            relatorio.getNomeProduto(),
                            relatorio.getQuantidadeVendida(),
                            relatorio.getFaturamentoPorItem());
                }
            }
            return ResponseEntity.ok("Relatório CSV gerado com sucesso: " + filePath);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
