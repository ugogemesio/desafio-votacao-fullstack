package com.dbserver.ugo.votacao.sessao;

import jakarta.validation.constraints.Min;

public record SessaoCreateDTO(

        @Min(1)
        Integer duracaoMinutos
) {}
