package com.dbserver.ugo.votacao.pauta;

import com.dbserver.ugo.votacao.resultado.Resultado;

public record PautaPatchDTO(
    String descricao,
    String titulo,
    PautaStatus status
    //Resultado resultado
) {}
