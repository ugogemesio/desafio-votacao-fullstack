package com.dbserver.ugo.votacao.sessao;

import com.dbserver.ugo.votacao.resultado.Resultado;

import java.time.LocalDateTime;

public record SessaoResponseDTO(
        Long id,
        LocalDateTime abertura,
        LocalDateTime fechamento,
        Integer duracaoMinutos,
        Long pautaId,
        SessaoStatus status,
        Resultado resultado
) { }
