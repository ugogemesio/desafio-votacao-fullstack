package com.dbserver.ugo.votacao.pauta;

import jakarta.validation.constraints.NotBlank;

public record PautaPutDTO(
        @NotBlank
        String descricao,
        @NotBlank
        String titulo
) {}
