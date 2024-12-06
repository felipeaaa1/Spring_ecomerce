package com.felipeAlves.ecommerce_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.felipeAlves.ecommerce_api.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
