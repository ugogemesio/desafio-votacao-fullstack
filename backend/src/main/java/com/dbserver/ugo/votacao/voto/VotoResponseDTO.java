package com.dbserver.ugo.votacao.voto;

public record VotoResponseDTO (

    Long id,
    Long idAssociado,
    Long idSessao,
    boolean valor
){}