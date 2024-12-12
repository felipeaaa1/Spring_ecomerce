package com.felipeAlves.ecommerce_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PagamentoResponseDTO(
        @JsonProperty("status") String status
) {}
