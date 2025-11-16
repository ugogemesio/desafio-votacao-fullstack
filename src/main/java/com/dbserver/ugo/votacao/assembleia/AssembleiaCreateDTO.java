package com.dbserver.ugo.votacao.assembleia;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class AssembleiaCreateDTO {


    @NotBlank(message = "O nome da assembleia é obrigatório")
    private String nomeAssembleia;

    @NotNull(message = "A data da assembleia é obrigatória")
    private LocalDateTime dataRealizacaoAssembleia;

    @NotBlank(message = "A descrição da assembleia é obrigatória")
    private String descricaoAssembleia;
}
