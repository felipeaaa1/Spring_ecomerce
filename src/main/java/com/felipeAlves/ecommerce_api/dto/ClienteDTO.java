package com.felipeAlves.ecommerce_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ClienteDTO(
        @NotNull(message = "O ID do cliente é obrigatório") Long id,
        @NotBlank(message = "O nome do cliente é obrigatório") @Size(max = 255) String nome,
        @Size(max = 50) String contato,
        String endereco,
        Boolean status) {
}
