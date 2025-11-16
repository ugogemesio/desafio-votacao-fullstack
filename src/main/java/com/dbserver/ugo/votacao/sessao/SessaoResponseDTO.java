package com.dbserver.ugo.votacao.sessao;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SessaoResponseDTO {

    private Long idSessao;
    private String assunto;
    private Long idPauta;
}
