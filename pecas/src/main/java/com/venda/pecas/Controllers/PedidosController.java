package com.venda.pecas.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.venda.pecas.Dtos.PedidosDto;
import com.venda.pecas.Models.Clientes;
import com.venda.pecas.Models.Pecas;
import com.venda.pecas.Models.Pedidos;
import com.venda.pecas.Repositories.ClientesRepository;
import com.venda.pecas.Repositories.PecasRepository;
import com.venda.pecas.Repositories.PedidosRepository;
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

    private final PedidosRepository pedidosRepository;
    private final ClientesRepository clientesRepository;
    private final PecasRepository pecasRepository;

    public PedidosController(PedidosRepository pedidosRepository, ClientesRepository clientesRepository,
            PecasRepository pecasRepository) {
        this.pedidosRepository = pedidosRepository;
        this.clientesRepository = clientesRepository;
        this.pecasRepository = pecasRepository;

    }

    @PostMapping("/criaPedido")
    public ResponseEntity<?> criaPedido(@RequestBody PedidosDto pedido) {
        Pecas pecas = pecasRepository.findById(pedido.peca_id())
                .orElseThrow(() -> new RuntimeException("Peça não encontrada"));
        Clientes cliente = clientesRepository.findById(pedido.cliente_id())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Pedidos pedidoSalvo = new Pedidos();
        pedidoSalvo.setPeca(pecas);
        pedidoSalvo.setCliente(cliente);
        pedidosRepository.save(pedidoSalvo);

        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoSalvo);
    }

    @GetMapping("/listaPedidos")
    public List<Pedidos> listaPedidos() {
        return pedidosRepository.findAll();
    }

    @DeleteMapping("/deletaPedido")
    public ResponseEntity<?> deletaPedido(@PathVariable Long id) {
        if (!pedidosRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido não encontrado!");
        }
        pedidosRepository.deleteById(id);
        return ResponseEntity.ok("Pedido deletado!");
    }

    @PutMapping("atualizaPedido/{id}")
    public ResponseEntity<?> atualizaPedido(@PathVariable Long id, @RequestBody Pedidos pedido) {
        if (!pedidosRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido não encontrado!");
        }
        pedido.setIdPedido(id);
        Pedidos pedidoAtualizado = pedidosRepository.save(pedido);
        return ResponseEntity.ok("Pedido atualizado!" + pedidoAtualizado);
    }

}
