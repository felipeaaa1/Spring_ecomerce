package com.felipeAlves.ecommerce_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felipeAlves.ecommerce_api.model.Cliente;
import com.felipeAlves.ecommerce_api.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente salvarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarClientePorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente atualizarCliente(Long id, Cliente clienteAtualizado) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setNomeCliente(clienteAtualizado.getNomeCliente());
                    cliente.setContato(clienteAtualizado.getContato());
                    cliente.setEndereco(clienteAtualizado.getEndereco());
                    cliente.setStatus(clienteAtualizado.getStatus());
                    cliente.setUsuarioAlteracao(clienteAtualizado.getUsuarioAlteracao());
                    cliente.setDataAtualizacao(clienteAtualizado.getDataAtualizacao());
                    return clienteRepository.save(cliente);
                })
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    public void deletarCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}
