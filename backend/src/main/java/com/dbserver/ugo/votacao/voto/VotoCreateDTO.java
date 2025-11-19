package com.dbserver.ugo.votacao.voto;

import jakarta.validation.constraints.NotNull;

public record VotoCreateDTO(
        @NotNull(message = "O ID do associado é obrigatório")
        String cpfAssociado,

        @NotNull(message = "O ID da sessão é obrigatório")
        Long idSessao,

        @NotNull(message = "O valor do voto é obrigatório")
        Boolean valor
) {}