package com.venda.pecas.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.venda.pecas.Models.Clientes;
import java.util.Optional;

public interface ClientesRepository extends JpaRepository<Clientes, Long> {
    Optional<Clientes> findByEmail(String email);
}
