package com.venda.pecas.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.venda.pecas.Models.Pecas;

public interface PecasRepository extends JpaRepository<Pecas, Long> {

}
