package com.dbserver.ugo.votacao.pauta;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PautaCreateDTO {

    @NotBlank(message = "A descrição da pauta é obrigatório")
    private String descricaoPauta;

    @NotBlank(message = "o título da pauta é obrigatório")
    private String titulo;

}
