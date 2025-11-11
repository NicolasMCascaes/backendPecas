package com.venda.pecas.Controllers;

import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.venda.pecas.Dtos.ClienteDto;
import com.venda.pecas.Dtos.ClienteLoginDto;
import com.venda.pecas.Dtos.ClienteResponseDto;
import com.venda.pecas.Models.Clientes;
import com.venda.pecas.Repositories.ClientesRepository;
import com.venda.pecas.Security.JwtUtil;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClientesRepository clientesRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public ClienteController(ClientesRepository clientesRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.clientesRepository = clientesRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("auth/cadastrarCliente")
    public ResponseEntity<?> cadastraCliente(@RequestBody ClienteDto cliente) {
        Clientes novoCliente = new Clientes();
        novoCliente.setNomeCompleto(cliente.nomeCompleto());
        novoCliente.setCpf(cliente.cpf());
        novoCliente.setDataDeNascimento(cliente.dataDenascimento());
        novoCliente.setEmail(cliente.email());
        novoCliente.setSenha(passwordEncoder.encode(cliente.senha()));
        novoCliente.setAtivo(true);
        clientesRepository.save(novoCliente);
        ClienteResponseDto resposta = new ClienteResponseDto(
                novoCliente.getNomeCompleto(),
                novoCliente.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @PostMapping("auth/login")
    public ResponseEntity<?> loginCliente(@RequestBody @Valid ClienteLoginDto clienteLogin) {
        Optional<Clientes> cliente = clientesRepository.findByEmail(clienteLogin.email());
        if (cliente.isPresent() && passwordEncoder.matches(clienteLogin.senha(), cliente.get().getSenha())) {
            String token = jwtUtil.generateToken(cliente.get().getEmail());
            Map<String, String> body = new HashMap<>();
            body.put("token", token);
            return ResponseEntity.ok(body);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Credenciais Inválidas!");
    }

    @GetMapping("/listarClientes")
    public ResponseEntity<List<Clientes>> clientes() {
        List<Clientes> list = clientesRepository.findAll();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/deletaCliente/{id}")
    public ResponseEntity<?> deletaCliente(@PathVariable Long id) {
        if (!clientesRepository.existsById(id)) {
            return ResponseEntity.status(404)
                    .body("Cliente não encontrado");
        }
        clientesRepository.deleteById(id);
        return ResponseEntity.ok("Cliente Deletado");
    }

    @PutMapping("/atualizarCliente/{id}")
    public ResponseEntity<?> atualizaCliente(@PathVariable Long id, @RequestBody Clientes clienteAtualizado) {
        if (!clientesRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuário não encontrado!");
        }
        clienteAtualizado.setIdCliente(id);
        Clientes cliente = clientesRepository.save(clienteAtualizado);
        return ResponseEntity.ok(cliente);
    }
}
