package com.felipeAlves.ecommerce_api.model.Utils;

public enum TipoUsuario {
    ADMIN("admin"),
    CLIENTE("cliente");

    private final String tipo;

    TipoUsuario(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
} 
