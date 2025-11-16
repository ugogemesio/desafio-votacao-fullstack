package com.dbserver.ugo.votacao.voto;

import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.NotNull;

@AllArgsConstructor
@Data
public class VotoCreateDTO {
    @NotNull(message = "O ID do associado é obrigatório")
    private Long idAssociado;

    @NotNull(message = "O ID da sessão é obrigatório")
    private Long idSessao;

    @NotNull(message = "O valor do voto é obrigatório")
    private Boolean valor;

}
