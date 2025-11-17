package com.dbserver.ugo.votacao.login;

import com.dbserver.ugo.votacao.utils.CpfValido;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank(message = "CPF é obrigatório")
        @CpfValido(message = "CPF deve conter 11 dígitos")
        String cpf
) {}
