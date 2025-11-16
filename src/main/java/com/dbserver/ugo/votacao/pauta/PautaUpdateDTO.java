package com.dbserver.ugo.votacao.pauta;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PautaUpdateDTO {

    @NotBlank(message = "Descrição da Pauta obrigatória")
    private String descricaoPauta;

    @NotBlank(message = "Título da Pauta obrigatória")
    private String titulo;

}
