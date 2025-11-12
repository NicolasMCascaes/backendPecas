package com.venda.pecas.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.venda.pecas.Dtos.PecaDto;
import com.venda.pecas.Dtos.PecaResponseDto;
import com.venda.pecas.Exceptions.ResourceNotFoundException;
import com.venda.pecas.Models.Pecas;
import com.venda.pecas.Repositories.PecasRepository;

@Service
public class PecasService {
    private final PecasRepository pecasRepository;

    public PecasService(PecasRepository pecasRepository) {
        this.pecasRepository = pecasRepository;
    }

    public PecaResponseDto cadastrarPeca(PecaDto peca) {
        Pecas novaPeca = new Pecas();
        novaPeca.setNomePeca(peca.nomePeca());
        novaPeca.setDescricao(peca.descricao());
        novaPeca.setCategoria(peca.categoria());
        novaPeca.setPrecoPeca(peca.precoPeca());
        novaPeca.setDisponivel(peca.disponivel());
        novaPeca.setQtdEstoque(peca.qtdEstoque());
        pecasRepository.save(novaPeca);
        return new PecaResponseDto(novaPeca.getNomePeca(), novaPeca.getDescricao(),
                novaPeca.getCategoria(), novaPeca.getPrecoPeca(), novaPeca.getQtdEstoque());
    }

    public List<Pecas> listarPecas() {
        return pecasRepository.findAll();
    }

    public void deletaPeca(Long id) {
        if (!pecasRepository.existsById(id)) {
            throw new ResourceNotFoundException("Peça não encontrada");
        }
        pecasRepository.deleteById(id);
    }

    public Pecas atualizaPeca(Long id, PecaDto novaPeca) {
        if (!pecasRepository.existsById(id)) {
            throw new ResourceNotFoundException("Peça não encontrada");
        }
        Pecas pecaAtualizada = new Pecas();
        pecaAtualizada.setIdPeca(id);
        pecaAtualizada.setNomePeca(novaPeca.nomePeca());
        pecaAtualizada.setDescricao(novaPeca.descricao());
        pecaAtualizada.setCategoria(novaPeca.categoria());
        pecaAtualizada.setDisponivel(novaPeca.disponivel());
        pecaAtualizada.setPrecoPeca(novaPeca.precoPeca());
        pecaAtualizada.setQtdEstoque(novaPeca.qtdEstoque());
        return pecasRepository.save(pecaAtualizada);
    }
}
