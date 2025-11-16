package com.dbserver.ugo.votacao.associado;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AssociadoCreateDTO {
    @NotBlank(message = "Nome do associado é obrigatório")
    private String nomeAssociado;
}
