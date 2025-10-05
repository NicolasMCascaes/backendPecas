package com.venda.pecas.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Pedidos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPedido;
    @ManyToOne
    @JoinColumn(name = "peca_id")
    Pecas peca;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    Clientes cliente;

    public Pedidos(long idPedido, Pecas peca, Clientes cliente) {
        this.idPedido = idPedido;
        this.peca = peca;
        this.cliente = cliente;
    }

    public long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(long idPedido) {
        this.idPedido = idPedido;
    }

    public Pecas getPeca() {
        return peca;
    }

    public void setPeca(Pecas peca) {
        this.peca = peca;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

}
