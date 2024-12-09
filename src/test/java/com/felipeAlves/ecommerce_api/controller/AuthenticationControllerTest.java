package com.felipeAlves.ecommerce_api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.felipeAlves.ecommerce_api.dto.AuthenticationDTO;
import com.felipeAlves.ecommerce_api.dto.LoginResponseDTO;
import com.felipeAlves.ecommerce_api.model.Usuario;
import com.felipeAlves.ecommerce_api.model.Utils.TipoUsuario;
import com.felipeAlves.ecommerce_api.security.TokenService;

class AuthenticationControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Test
    void deveRetornarTokenValidoAoRealizarLogin() {
        // Arrange
        MockitoAnnotations.openMocks(this);
        AuthenticationDTO loginRequest = new AuthenticationDTO("felipealves", "senhaSegura");

        Usuario mockUsuario = new Usuario("felipealves", "felipe@example.com", "senhaEncriptada", TipoUsuario.CLIENTE);
        String mockToken = "mock-jwt-token";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(mockUsuario, null));
        when(tokenService.generateToken(mockUsuario)).thenReturn(mockToken);

        // Act
        ResponseEntity<LoginResponseDTO> response = authenticationController.login(loginRequest);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(mockToken, response.getBody().token());
    }

    @Test
    void deveLancarExcecaoParaCredenciaisInvalidas() {
        // Arrange
        MockitoAnnotations.openMocks(this);
        AuthenticationDTO loginRequest = new AuthenticationDTO("felipealves", "senhaIncorreta");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Credenciais inválidas"));

        // Act & Assert
        try {
            authenticationController.login(loginRequest);
        } catch (BadCredentialsException e) {
            assertEquals("Credenciais inválidas", e.getMessage());
        }
    }
}
