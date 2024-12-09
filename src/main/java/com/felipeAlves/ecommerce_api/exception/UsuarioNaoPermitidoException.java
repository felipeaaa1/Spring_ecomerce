package com.felipeAlves.ecommerce_api.exception;

public class UsuarioNaoPermitidoException extends RuntimeException {
    public UsuarioNaoPermitidoException(String message) {
        super(message);
    }
}
