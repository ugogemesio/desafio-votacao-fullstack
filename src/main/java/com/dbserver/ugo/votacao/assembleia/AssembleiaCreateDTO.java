package com.dbserver.ugo.votacao.assembleia;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class AssembleiaCreateDTO {

    @NotBlank(message = "O nome da assembleia é obrigatório")
    private String nomeAssembleia;

    @NotNull(message = "A data da assembleia é obrigatória")
    private LocalDateTime dataRealizacaoAssembleia;

    @NotNull(message = "A descrição da assembleia é obrigatória")
    private String descricaoAssembleia;

    @NotNull(message = "A descrição da assembleia é obrigatória")
    private LocalDateTime dataRealizacaoAssembleia;


}
