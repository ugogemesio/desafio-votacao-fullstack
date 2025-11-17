package com.dbserver.ugo.votacao.sessao;

import java.time.LocalDateTime;

public record SessaoResponseDTO(
        Long id,
        LocalDateTime abertura,
        LocalDateTime fechamento,
        Integer duracaoMinutos,
        Long pautaId
) {}
