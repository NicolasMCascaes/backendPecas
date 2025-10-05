package com.venda.pecas.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.venda.pecas.Models.Pedidos;

public interface PedidosRepository extends JpaRepository<Pedidos, Long> {

}
