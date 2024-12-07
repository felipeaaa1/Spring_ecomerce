package com.felipeAlves.ecommerce_api.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record registerDTO(
    @NotBlank String login,
    @NotBlank String email,
    @NotBlank String password,
    @NotNull TipoUsuario tipo
) {}
