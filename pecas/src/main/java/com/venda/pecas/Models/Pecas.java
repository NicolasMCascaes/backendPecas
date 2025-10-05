package com.venda.pecas.Models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Pecas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPeca;
    private String nomePeca;
    private String descricao;
    private String categoria;
    private double precoPeca;
    private int qtdEstoque;
    private boolean disponivel;
    @OneToMany(mappedBy = "peca")
    @JsonIgnore
    List<Pedidos> pedido;

    public Pecas(String nomePeca, String descricao, String categoria, double precoPeca, int qtdEstoque,
            boolean disponivel) {
        this.nomePeca = nomePeca;
        this.descricao = descricao;
        this.categoria = categoria;
        this.precoPeca = precoPeca;
        this.qtdEstoque = qtdEstoque;
        this.disponivel = disponivel;
    }

    public Pecas() {

    }

    public long getIdPeca() {
        return idPeca;
    }

    public void setIdPeca(long idPeca) {
        this.idPeca = idPeca;
    }

    public String getNomePeca() {
        return nomePeca;
    }

    public void setNomePeca(String nomePeca) {
        this.nomePeca = nomePeca;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecoPeca() {
        return precoPeca;
    }

    public void setPrecoPeca(double precoPeca) {
        this.precoPeca = precoPeca;
    }

    public int getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

}
