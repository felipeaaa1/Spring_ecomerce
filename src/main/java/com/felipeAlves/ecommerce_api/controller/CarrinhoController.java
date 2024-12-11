package com.felipeAlves.ecommerce_api.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.felipeAlves.ecommerce_api.model.ItemCarrinho;
import com.felipeAlves.ecommerce_api.service.CarrinhoService;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @PostMapping("/adicionar")
    public ResponseEntity<List<ItemCarrinho>> adicionarItem(@RequestBody ItemCarrinho itemCarrinho) {
        carrinhoService.adicionarItem(itemCarrinho);
        return ResponseEntity.ok(carrinhoService.listarItens());
    }

    @DeleteMapping("/remover")
    public ResponseEntity<List<ItemCarrinho>> removerItem(@RequestBody ItemCarrinho itemCarrinho) {
        carrinhoService.removerItem(itemCarrinho);
        return ResponseEntity.ok(carrinhoService.listarItens());
    }

    @GetMapping("/itens")
    public ResponseEntity<List<ItemCarrinho>> listarItens() {
        return ResponseEntity.ok(carrinhoService.listarItens());
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> calcularTotal() {
        return ResponseEntity.ok(carrinhoService.calcularTotal());
    }

    @PostMapping("/finalizar")
    public ResponseEntity<Void> finalizarCompra() {
        carrinhoService.finalizarCompra();
        return ResponseEntity.ok().build();
    }
}
