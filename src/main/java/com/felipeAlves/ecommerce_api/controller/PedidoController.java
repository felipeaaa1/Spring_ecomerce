package com.felipeAlves.ecommerce_api.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.felipeAlves.ecommerce_api.model.Pedido;
import com.felipeAlves.ecommerce_api.model.Usuario;
import com.felipeAlves.ecommerce_api.service.PedidoService;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Pedido> criarPedido(@RequestBody Pedido pedido) {
        Pedido novoPedido = pedidoService.salvarPedido(pedido, getAuthenticatedUser());
        return ResponseEntity.ok(novoPedido);
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listarPedidos() {
        List<Pedido> pedidos = pedidoService.listarPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPedidoPorId(@PathVariable Long id) {
        return pedidoService.buscarPedidoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/filtros")
    public ResponseEntity<List<Pedido>> buscarPedidosComFiltros(
            @RequestParam(value = "clienteId", required = false) Long clienteId,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "dataInicio", required = false) String dataInicio,
            @RequestParam(value = "dataFim", required = false) String dataFim) {
        LocalDateTime inicio = dataInicio != null ? LocalDateTime.parse(dataInicio) : null;
        LocalDateTime fim = dataFim != null ? LocalDateTime.parse(dataFim) : null;

        List<Pedido> pedidos = pedidoService.buscarPedidosComFiltros(clienteId, status, inicio, fim);
        return ResponseEntity.ok(pedidos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizarPedido(@PathVariable Long id, @RequestBody Pedido pedido) {
            Pedido pedidoAtualizado = pedidoService.atualizarPedido(id, pedido, getAuthenticatedUser());
            return ResponseEntity.ok(pedidoAtualizado); 
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        pedidoService.deletarPedido(id);
        return ResponseEntity.noContent().build();
    }

    private Usuario getAuthenticatedUser() {
        return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
