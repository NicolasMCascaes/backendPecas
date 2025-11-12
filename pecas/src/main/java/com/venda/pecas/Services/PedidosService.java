package com.venda.pecas.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.venda.pecas.Dtos.PedidosDto;
import com.venda.pecas.Exceptions.ResourceNotFoundException;
import com.venda.pecas.Models.Clientes;
import com.venda.pecas.Models.Pecas;
import com.venda.pecas.Models.Pedidos;
import com.venda.pecas.Repositories.ClientesRepository;
import com.venda.pecas.Repositories.PecasRepository;
import com.venda.pecas.Repositories.PedidosRepository;

@Service
public class PedidosService {
    private final PedidosRepository pedidosRepository;
    private final ClientesRepository clientesRepository;
    private final PecasRepository pecasRepository;

    public PedidosService(PedidosRepository pedidosRepository, ClientesRepository clientesRepository,
            PecasRepository pecasRepository) {
        this.pedidosRepository = pedidosRepository;
        this.clientesRepository = clientesRepository;
        this.pecasRepository = pecasRepository;
    }

    public Pedidos criaPedido(PedidosDto pedidoDto) {
        Pecas pecas = pecasRepository.findById(pedidoDto.peca_id())
                .orElseThrow(() -> new ResourceNotFoundException("Peça não encontrada"));
        Clientes cliente = clientesRepository.findById(pedidoDto.cliente_id())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        Pedidos pedidoSalvo = new Pedidos();
        pedidoSalvo.setPeca(pecas);
        pedidoSalvo.setCliente(cliente);
        return pedidosRepository.save(pedidoSalvo);
    }

    public List<Pedidos> listarPedidos() {
        return pedidosRepository.findAll();
    }

    public void deletaPedido(Long id) {
        if (!pedidosRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pedido não encontrado!");
        }
        pedidosRepository.deleteById(id);
    }

    public Pedidos atualizaPedido(Long id, Pedidos pedido) {
        if (!pedidosRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pedido não encontrado!");
        }
        pedido.setIdPedido(id);
        return pedidosRepository.save(pedido);
    }
}
