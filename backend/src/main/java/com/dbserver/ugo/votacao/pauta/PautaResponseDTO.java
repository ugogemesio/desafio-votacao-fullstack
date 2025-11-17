package com.dbserver.ugo.votacao.pauta;

import java.time.LocalDateTime;

public record PautaResponseDTO (

    Long id,
    String descricao,
    String titulo,
    LocalDateTime dataCriacao

){}
