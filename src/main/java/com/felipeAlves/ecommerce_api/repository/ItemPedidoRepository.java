package com.felipeAlves.ecommerce_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.felipeAlves.ecommerce_api.model.ItemPedido;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long>{
	
    List<ItemPedido> findByPedidoIdPedido(Long idPedido);


}
