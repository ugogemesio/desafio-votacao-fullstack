package com.dbserver.ugo.votacao.pauta;

import lombok.Data;

@Data
public class PautaResponseDTO {

    private Long idPauta;
    private String descricaoPauta;
    private String titulo;
    private Long idAssembleia;
}
