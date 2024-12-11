package com.felipeAlves.ecommerce_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.felipeAlves.ecommerce_api.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	Optional<Cliente> findByIdCliente(Long idCliente);
	
	Cliente findByUsuarioIdUsuario(Long idUsuario);
}
