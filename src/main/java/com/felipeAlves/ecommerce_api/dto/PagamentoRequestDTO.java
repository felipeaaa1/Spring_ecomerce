package com.felipeAlves.ecommerce_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record PagamentoRequestDTO(
        @JsonProperty("pedido_id") Long pedidoId,
        @JsonProperty("valor_total") BigDecimal valorTotal
) {}
