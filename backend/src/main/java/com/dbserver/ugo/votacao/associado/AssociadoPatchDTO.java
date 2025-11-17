package com.dbserver.ugo.votacao.associado;

import com.dbserver.ugo.votacao.utils.CpfValido;

public record AssociadoPatchDTO(
        String nome,
        @CpfValido
        String cpf
) {}
