package com.dbserver.ugo.votacao.voto;

import lombok.Data;

@Data
public class VotoResponseDTO {

    private Long idVoto;
    private Long idAssociado;
    private Long idSessao;
    private boolean valor;
}
