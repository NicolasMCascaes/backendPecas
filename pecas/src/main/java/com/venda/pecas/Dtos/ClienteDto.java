package com.venda.pecas.Dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public record ClienteDto(@NotBlank(message = "O nome não pode estar em branco!") String nomeCompleto,
        @NotBlank(message = "O cpf não pode estar em branco!") String cpf,
        LocalDate dataDenascimento, @NotBlank(message = "O email não pode estar em branco!") String email,
        @NotBlank(message = "A senha não pode estar em branco!") String senha) {

}
