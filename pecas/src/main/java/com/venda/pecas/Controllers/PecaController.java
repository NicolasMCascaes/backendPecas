package com.venda.pecas.Controllers;

import java.util.List;

import com.venda.pecas.Exceptions.ResourceNotFoundException;
import com.venda.pecas.Dtos.PecaDto;
import com.venda.pecas.Dtos.PecaResponseDto;
import com.venda.pecas.Models.Pecas;
import com.venda.pecas.Services.PecasService;

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
@RequestMapping("/pecas")
public class PecaController {
    private final PecasService pecasService;

    public PecaController(PecasService pecasService) {
        this.pecasService = pecasService;
    }

    @PostMapping("/cadastraPeca")
    public ResponseEntity<?> postMethodName(@RequestBody @Valid PecaDto peca) {
        PecaResponseDto resposta = pecasService.cadastrarPeca(peca);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @GetMapping("/listaPecas")
    public ResponseEntity<List<Pecas>> listaPecas() {
        List<Pecas> list = pecasService.listarPecas();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/deletaPecas/{id}")
    public ResponseEntity<?> deletaPeca(@PathVariable Long id) {
        try {
            pecasService.deletaPeca(id);
            return ResponseEntity.ok("Peca deletada!");
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PutMapping("atualizarPeca/{id}")
    public ResponseEntity<?> atualizaPeca(@PathVariable Long id, @RequestBody PecaDto novaPeca) {
        try {
            Pecas pecaAtualizada = pecasService.atualizaPeca(id, novaPeca);
            return ResponseEntity.ok("Peca atualizada" + pecaAtualizada);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
