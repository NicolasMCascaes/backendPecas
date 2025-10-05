package com.venda.pecas.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.venda.pecas.Repositories.PedidosRepository;

@RestController
@RequestMapping("/pedidos")
public class PedidosController {
    private final PedidosRepository pedidosRepository;

    public PedidosController(PedidosRepository pedidosRepository) {
        this.pedidosRepository = pedidosRepository;
    }
}
