package com.venda.pecas.Dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClienteLoginDto(
        @Email(message = "Email Inválido!") String email,
        @NotBlank(message = "A senha não pode estar em branco!") String senha) {

}
