package com.felipeAlves.ecommerce_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.felipeAlves.ecommerce_api.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    UserDetails findByLogin(String login);

    UserDetails findByIdUsuario(Long idUsuario);

    UserDetails findByEmail(String email);

}
