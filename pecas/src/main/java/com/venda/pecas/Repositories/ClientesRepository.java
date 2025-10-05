package com.venda.pecas.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.venda.pecas.Models.Clientes;

public interface ClientesRepository extends JpaRepository<Clientes, Long> {

}
