package com.dbserver.ugo.votacao.associado;

import com.dbserver.ugo.votacao.utils.CpfValido;
import jakarta.validation.constraints.NotBlank;

public record AssociadoPutDTO(
        @NotBlank
        String nome,
        @NotBlank
        @CpfValido
        String cpf
) {}
