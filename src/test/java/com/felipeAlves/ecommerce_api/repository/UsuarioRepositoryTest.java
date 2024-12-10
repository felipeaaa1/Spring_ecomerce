package com.felipeAlves.ecommerce_api.repository;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.felipeAlves.ecommerce_api.dto.registerDTO;
import com.felipeAlves.ecommerce_api.exception.EmailJaCadastradoException;
import com.felipeAlves.ecommerce_api.exception.UsuarioJaExisteException;
import com.felipeAlves.ecommerce_api.mailService.EmailService;
import com.felipeAlves.ecommerce_api.model.Usuario;
import com.felipeAlves.ecommerce_api.model.Utils.TipoUsuario;
import com.felipeAlves.ecommerce_api.service.UsuarioService;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;
    
    @Mock
    private EmailService emailService;

    @Test
    void deveCadastrarUsuarioComSucesso() {
        // Arrange
        MockitoAnnotations.openMocks(this);

        registerDTO novoUsuario = new registerDTO("felipealves", "emailteste@example.com", "senha123", TipoUsuario.ADMIN);

        // Simula que não há conflitos com login ou email
        when(usuarioRepository.findByLogin(novoUsuario.login())).thenReturn(null);
        when(usuarioRepository.findByEmail(novoUsuario.email())).thenReturn(null);

        // Act & Assert
        assertDoesNotThrow(() -> usuarioService.salvar(novoUsuario));

        // Verifica se o método save foi chamado
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void deveLancarExcecaoQuandoLoginJaExistir() {
        // Arrange
        MockitoAnnotations.openMocks(this);

        registerDTO usuarioDuplicado = new registerDTO("felipealves", "felipe@example.com", "senha123", TipoUsuario.ADMIN);
        when(usuarioRepository.findByLogin(usuarioDuplicado.login())).thenReturn(new Usuario());

        // Act & Assert
        assertThrows(UsuarioJaExisteException.class, () -> usuarioService.salvar(usuarioDuplicado));
    }

    @Test
    void deveLancarExcecaoQuandoEmailJaExistir() {
        // Arrange
        MockitoAnnotations.openMocks(this);

        registerDTO usuarioDuplicado = new registerDTO("novoLogin", "felipe@example.com", "senha123", TipoUsuario.CLIENTE);
        when(usuarioRepository.findByEmail(usuarioDuplicado.email())).thenReturn(new Usuario());

        // Act & Assert
        assertThrows(EmailJaCadastradoException.class, () -> usuarioService.salvar(usuarioDuplicado));
    }
}
