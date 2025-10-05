package com.venda.pecas.Models;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Clientes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCliente;
    @Column(unique = true, nullable = false)
    private String nomeCompleto;
    @Column(unique = true)
    private String cpf;
    private LocalDate dataDeNascimento;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String senha;
    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Pedidos> pedido;

    public Clientes(long idCliente, String nomeCompleto, String cpf, LocalDate dataDeNascimento, String email,
            String senha) {
        this.idCliente = idCliente;
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.dataDeNascimento = dataDeNascimento;
        this.email = email;
        this.senha = senha;
    }

    public Clientes() {
    }

    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
