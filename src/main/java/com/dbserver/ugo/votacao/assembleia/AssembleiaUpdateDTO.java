package com.dbserver.ugo.votacao.assembleia;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@AllArgsConstructor
@Data
public class AssembleiaUpdateDTO {
    private String nomeAssembleia;
    private String descricaoAssembleia;
    private LocalDateTime dataRealizacaoAssembleia;
}
