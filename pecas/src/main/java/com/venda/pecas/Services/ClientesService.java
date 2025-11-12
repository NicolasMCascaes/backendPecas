package com.venda.pecas.Services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;

import com.venda.pecas.Exceptions.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.venda.pecas.Dtos.ClienteDto;
import com.venda.pecas.Dtos.ClienteLoginDto;
import com.venda.pecas.Dtos.ClienteResponseDto;
import com.venda.pecas.Models.Clientes;
import com.venda.pecas.Repositories.ClientesRepository;
import com.venda.pecas.Security.JwtUtil;

@Service
public class ClientesService {
    private final ClientesRepository clientesRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public ClientesService(ClientesRepository clientesRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.clientesRepository = clientesRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public ClienteResponseDto cadastrarCliente(ClienteDto cliente) {
        Clientes novoCliente = new Clientes();
        novoCliente.setNomeCompleto(cliente.nomeCompleto());
        novoCliente.setCpf(cliente.cpf());
        novoCliente.setDataDeNascimento(cliente.dataDenascimento());
        novoCliente.setEmail(cliente.email());
        novoCliente.setSenha(passwordEncoder.encode(cliente.senha()));
        novoCliente.setAtivo(true);
        clientesRepository.save(novoCliente);
        return new ClienteResponseDto(novoCliente.getNomeCompleto(), novoCliente.getEmail());
    }

    public Optional<String> login(ClienteLoginDto clienteLogin) {
        Optional<Clientes> cliente = clientesRepository.findByEmail(clienteLogin.email());
        if (cliente.isPresent() && passwordEncoder.matches(clienteLogin.senha(), cliente.get().getSenha())) {
            String token = jwtUtil.generateToken(cliente.get().getEmail());
            return Optional.of(token);
        }
        return Optional.empty();
    }

    public List<Clientes> listarClientes() {
        return clientesRepository.findAll();
    }

    public void deletaCliente(Long id) {
        if (!clientesRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente não encontrado");
        }
        clientesRepository.deleteById(id);
    }

    public Clientes atualizaCliente(Long id, Clientes clienteAtualizado) {
        if (!clientesRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não encontrado!");
        }
        clienteAtualizado.setIdCliente(id);
        return clientesRepository.save(clienteAtualizado);
    }
}
