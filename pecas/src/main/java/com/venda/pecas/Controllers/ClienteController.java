package com.venda.pecas.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.venda.pecas.Dtos.ClienteLoginDto;
import com.venda.pecas.Models.Clientes;
import com.venda.pecas.Repositories.ClientesRepository;
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

    public ClienteController(ClientesRepository clientesRepository, PasswordEncoder passwordEncoder) {
        this.clientesRepository = clientesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/cadastrarCliente")
    public Clientes cadastraCliente(@RequestBody Clientes clientes) {
        return clientesRepository.save(clientes);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginCliente(@RequestBody ClienteLoginDto clienteLogin) {
        Optional<Clientes> cliente = clientesRepository.findByEmail(clienteLogin.email());
        if (cliente.isPresent() && passwordEncoder.matches(clienteLogin.senha(), cliente.get().getSenha())) {
            return ResponseEntity.ok("Logado com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Credenciais Inválidas!");
    }

    @GetMapping("/listarClientes")
    public List<Clientes> clientes() {
        return clientesRepository.findAll();
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
