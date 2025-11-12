package com.venda.pecas.Controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;

import com.venda.pecas.Exceptions.ResourceNotFoundException;
import com.venda.pecas.Dtos.ClienteDto;
import com.venda.pecas.Dtos.ClienteLoginDto;
import com.venda.pecas.Dtos.ClienteResponseDto;
import com.venda.pecas.Models.Clientes;
import com.venda.pecas.Services.ClientesService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private final ClientesService clientesService;

    public ClienteController(ClientesService clientesService) {
        this.clientesService = clientesService;
    }

    @PostMapping("auth/cadastrarCliente")
    public ResponseEntity<?> cadastraCliente(@RequestBody ClienteDto cliente) {
        ClienteResponseDto resposta = clientesService.cadastrarCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @PostMapping("auth/login")
    public ResponseEntity<?> loginCliente(@RequestBody @Valid ClienteLoginDto clienteLogin) {
        Optional<String> token = clientesService.login(clienteLogin);
        if (token.isPresent()) {
            Map<String, String> body = new HashMap<>();
            body.put("token", token.get());
            return ResponseEntity.ok(body);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais Inválidas!");
    }

    @GetMapping("/listarClientes")
    public ResponseEntity<List<Clientes>> clientes() {
        List<Clientes> list = clientesService.listarClientes();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/deletaCliente/{id}")
    public ResponseEntity<?> deletaCliente(@PathVariable Long id) {
        try {
            clientesService.deletaCliente(id);
            return ResponseEntity.ok("Cliente Deletado");
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PutMapping("/atualizarCliente/{id}")
    public ResponseEntity<?> atualizaCliente(@PathVariable Long id, @RequestBody Clientes clienteAtualizado) {
        try {
            Clientes cliente = clientesService.atualizaCliente(id, clienteAtualizado);
            return ResponseEntity.ok(cliente);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
