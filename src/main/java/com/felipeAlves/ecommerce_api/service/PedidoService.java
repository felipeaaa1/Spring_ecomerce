package com.felipeAlves.ecommerce_api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.felipeAlves.ecommerce_api.model.Cliente;
import com.felipeAlves.ecommerce_api.model.Pedido;
import com.felipeAlves.ecommerce_api.model.Usuario;
import com.felipeAlves.ecommerce_api.repository.ClienteRepository;
import com.felipeAlves.ecommerce_api.repository.PedidoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public Pedido salvarPedido(Pedido pedido, Usuario usuarioLogado) {
        if (pedido == null) {
            throw new IllegalArgumentException("Dados do pedido são obrigatórios.");
        }

        validarCliente(pedido.getFkCliente());

        pedido.setUsuarioCriacao(usuarioLogado.getIdUsuario());
        pedido.setUsuarioAtualizacao(usuarioLogado.getIdUsuario());
        pedido.setDataPedido(LocalDateTime.now());
        calcularTotal(pedido);

        try {
            return pedidoRepository.save(pedido);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Erro ao salvar pedido. Verifique os dados. \n" + e.getMessage());
        }
    }

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> buscarPedidoPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    public Pedido atualizarPedido(Long idPedido, Pedido pedidoAtualizado, Usuario usuarioLogado) {
        Pedido pedidoExistente = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado."));

        validarCliente(pedidoAtualizado.getFkCliente());
        calcularTotal(pedidoAtualizado);

        pedidoExistente.setCliente(clienteRepository.findByIdCliente(pedidoAtualizado.getCliente().getIdCliente())
        	    .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado.")));
        pedidoExistente.setStatus(pedidoAtualizado.getStatus());
        pedidoExistente.setTotal(pedidoAtualizado.getTotal());
        pedidoExistente.setUsuarioAtualizacao(usuarioLogado.getIdUsuario());

        return pedidoRepository.save(pedidoExistente);
    }

    public List<Pedido> buscarPedidosComFiltros(Long clienteId, String status, LocalDateTime dataInicio, LocalDateTime dataFim) {
        // Implementar lógica de busca por filtros
        // Exemplo: Adaptar usando `Specification` ou construir queries diretamente no repositório
        return pedidoRepository.findAll(); // Substituir com filtros específicos
    }

    public void deletarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado."));
        pedidoRepository.delete(pedido);
    }

    private void calcularTotal(Pedido pedido) {
//        if (pedido.get != null) {
//            BigDecimal total = pedido.getItens().stream()
//                    .map(item -> item.getPrecoPorUnidade().multiply(BigDecimal.valueOf(item.getQuantidade())))
//                    .reduce(BigDecimal.ZERO, BigDecimal::add);
//            pedido.setTotal(total);
//        } else {
//            pedido.setTotal(BigDecimal.ZERO);
//        }
    }

    private void validarCliente(Long clienteId) {
        Optional<Cliente> cliente = clienteRepository.findById(clienteId);
        if (cliente.isEmpty() || !cliente.get().getStatus()) {
            throw new IllegalArgumentException("Cliente inválido ou inativo.");
        }
    }
}
