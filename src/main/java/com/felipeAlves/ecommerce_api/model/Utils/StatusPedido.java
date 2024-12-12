package com.felipeAlves.ecommerce_api.model.Utils;

public enum StatusPedido {
	AGUARDANDO_PAGAMENTO("aguardando_pagamento"),
	PAGAMENTO_EFETUADO("pagamento_efetuado"),
	PAGAMENTO_NEGADO("pagamento_negado"),
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
