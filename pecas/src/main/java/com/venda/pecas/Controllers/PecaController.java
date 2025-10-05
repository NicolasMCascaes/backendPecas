package com.venda.pecas.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.venda.pecas.Dtos.PecaDto;
import com.venda.pecas.Dtos.PecaResponseDto;
import com.venda.pecas.Models.Pecas;
import com.venda.pecas.Repositories.PecasRepository;

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
    @Autowired
    private PecasRepository pecasRepository;

    @PostMapping("/cadastraPeca")
    public ResponseEntity<?> postMethodName(@RequestBody @Valid PecaDto peca) {
        Pecas novaPeca = new Pecas();
        novaPeca.setNomePeca(peca.nomePeca());
        novaPeca.setDescricao(peca.descricao());
        novaPeca.setCategoria(peca.categoria());
        novaPeca.setPrecoPeca(peca.precoPeca());
        novaPeca.setDisponivel(peca.disponivel());
        pecasRepository.save(novaPeca);
        PecaResponseDto resposta = new PecaResponseDto(novaPeca.getNomePeca(), novaPeca.getDescricao(),
                novaPeca.getCategoria(), novaPeca.getPrecoPeca(), novaPeca.getQtdEstoque());
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @GetMapping("/listaPecas")
    public List<Pecas> listaPecas() {
        return pecasRepository.findAll();
    }

    @DeleteMapping("/deletaPecas")
    public ResponseEntity<?> deletaPeca(@PathVariable Long id) {
        if (!pecasRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Peça não encontrada");
        }
        pecasRepository.deleteById(id);
        return ResponseEntity.ok("Peca deletada!");
    }

    @PutMapping("atualizarPeca/{id}")
    public ResponseEntity<?> atualizaPeca(@PathVariable Long id, @RequestBody PecaDto novaPeca) {
        if (!pecasRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Peça não encontrada");
        }
        Pecas pecaAtualizada = new Pecas();
        pecaAtualizada.setNomePeca(novaPeca.nomePeca());
        pecaAtualizada.setDescricao(novaPeca.descricao());
        pecaAtualizada.setCategoria(novaPeca.categoria());
        pecaAtualizada.setDisponivel(novaPeca.disponivel());
        pecaAtualizada.setPrecoPeca(novaPeca.precoPeca());
        pecaAtualizada.setQtdEstoque(novaPeca.qtdEstoque());
        pecasRepository.save(pecaAtualizada);
        return ResponseEntity.ok("Peca atualizada" + pecaAtualizada);
    }

}
