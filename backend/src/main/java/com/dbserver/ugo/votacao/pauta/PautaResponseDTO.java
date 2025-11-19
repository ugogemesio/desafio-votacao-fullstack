package com.dbserver.ugo.votacao.pauta;

import com.dbserver.ugo.votacao.resultado.Resultado;

import java.time.LocalDateTime;

public record PautaResponseDTO (

    Long id,
    String descricao,
    String titulo,
    LocalDateTime dataCriacao,
    PautaStatus status,
    Resultado resultado
){}
