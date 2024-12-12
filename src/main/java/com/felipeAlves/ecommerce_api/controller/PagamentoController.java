package com.felipeAlves.ecommerce_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.felipeAlves.ecommerce_api.dto.PagamentoResponseDTO;
import com.felipeAlves.ecommerce_api.service.PagamentoService;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping("/processar/{pedidoId}")
    public ResponseEntity<?> processarPagamento(@PathVariable Long pedidoId) {
        try {
            String status = pagamentoService.pagamentoAvulso(pedidoId);
            return ResponseEntity.ok("Status do pagamento: " + status);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao processar pagamento: " + e.getMessage());
        }
    }
}