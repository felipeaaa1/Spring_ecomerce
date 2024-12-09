package com.felipeAlves.ecommerce_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.felipeAlves.ecommerce_api.exception.UsuarioNaoPermitidoException;
import com.felipeAlves.ecommerce_api.model.Cliente;
import com.felipeAlves.ecommerce_api.model.Usuario;
import com.felipeAlves.ecommerce_api.model.Utils.TipoUsuario;
import com.felipeAlves.ecommerce_api.repository.ClienteRepository;
import com.felipeAlves.ecommerce_api.repository.UsuarioRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Cliente salvarCliente(Cliente cliente, Usuario usuarioLogado) {
        if (cliente.getUsuario() == null || cliente.getUsuario().getIdUsuario() == null) {
            throw new IllegalArgumentException("Cliente deve estar vinculado a um usuário existente.");
        }

        Usuario usuario = (Usuario) usuarioRepository.findByIdUsuario(cliente.getUsuario().getIdUsuario());
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado para o ID fornecido.");
        }

        cliente.setUsuario(usuario);
        cliente.setUsuarioCriacao(usuarioLogado.getIdUsuario()); // Define o usuário que está criando o cliente
        cliente.setUsuarioAlteracao(usuarioLogado.getIdUsuario()); // Define o usuário que está criando o cliente

        try {
            return clienteRepository.save(cliente);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Usuário já está vinculado a outro cliente.");
        }
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarClientePorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente atualizarCliente(Long id, Cliente clienteAtualizado, Usuario usuarioLogado) {
    	
    	System.out.println(usuarioLogado.toString());
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Verificar se o usuário logado é ADMIN ou está tentando editar seu próprio cliente
        if (!usuarioLogado.getTipo().equals(TipoUsuario.ADMIN) &&
                !usuarioLogado.getIdUsuario().equals(clienteExistente.getUsuario().getIdUsuario())) {
            throw new UsuarioNaoPermitidoException("Usuário não tem permissão para editar este cliente.");
        }

        clienteExistente.setNomeCliente(clienteAtualizado.getNomeCliente());
        clienteExistente.setContato(clienteAtualizado.getContato());
        clienteExistente.setEndereco(clienteAtualizado.getEndereco());
        clienteExistente.setStatus(clienteAtualizado.getStatus());
        clienteExistente.setUsuarioAlteracao(usuarioLogado.getIdUsuario()); 
        clienteExistente.setDataAtualizacao(clienteAtualizado.getDataAtualizacao());

        return clienteRepository.save(clienteExistente);
    }

    public void deletarCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}
