package com.dbserver.ugo.votacao.associado;

import jakarta.validation.constraints.NotBlank;

public record AssociadoPutDTO(
        @NotBlank
        String nome,
        @NotBlank
        String cpf
) {}
