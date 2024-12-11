package com.felipeAlves.ecommerce_api.model.Utils;

public enum StatusPedido {
    RECEBIDO("recebido"),
    EM_PREPARACAO("em_preparacao"),
    DESPACHADO("despachado"),
    ENTREGUE("entregue");

    private final String status;

    StatusPedido(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
