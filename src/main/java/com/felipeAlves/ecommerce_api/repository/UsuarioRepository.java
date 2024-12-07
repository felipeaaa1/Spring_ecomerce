package com.felipeAlves.ecommerce_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.felipeAlves.ecommerce_api.user.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	UserDetails findBylogin(String login);

}
