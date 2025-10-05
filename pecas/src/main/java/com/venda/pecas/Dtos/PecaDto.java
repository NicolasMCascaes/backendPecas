package com.venda.pecas.Dtos;

import jakarta.validation.constraints.NotBlank;

public record PecaDto(@NotBlank(message = "O nome da peça não pode estar em branco!") String nomePeca, String descricao,
        @NotBlank(message = "A categoria não pode estar em branco") String categoria,
        @NotBlank(message = "O preço não pode estar em branco!") double precoPeca,
        @NotBlank(message = "A quantidade em estoque não pode estar em branco") int qtdEstoque,
        boolean disponivel) {

}
