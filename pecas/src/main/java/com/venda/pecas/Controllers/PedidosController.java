package com.venda.pecas.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.venda.pecas.Exceptions.ResourceNotFoundException;
import com.venda.pecas.Dtos.PedidosDto;
import com.venda.pecas.Models.Pedidos;
import com.venda.pecas.Services.PedidosService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/pedidos")
public class PedidosController {

    private final PedidosService pedidosService;

    public PedidosController(PedidosService pedidosService) {
        this.pedidosService = pedidosService;
    }

    @PostMapping("/criaPedido")
    public ResponseEntity<?> criaPedido(@RequestBody PedidosDto pedido) {
        Pedidos pedidoSalvo = pedidosService.criaPedido(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoSalvo);
    }

    @GetMapping("/listaPedidos")
    public ResponseEntity<List<Pedidos>> listaPedidos() {
        List<Pedidos> list = pedidosService.listarPedidos();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/deletaPedido/{id}")
    public ResponseEntity<?> deletaPedido(@PathVariable Long id) {
        try {
            pedidosService.deletaPedido(id);
            return ResponseEntity.ok("Pedido deletado!");
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PutMapping("atualizaPedido/{id}")
    public ResponseEntity<?> atualizaPedido(@PathVariable Long id, @RequestBody Pedidos pedido) {
        try {
            Pedidos pedidoAtualizado = pedidosService.atualizaPedido(id, pedido);
            return ResponseEntity.ok("Pedido atualizado!" + pedidoAtualizado);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

}
