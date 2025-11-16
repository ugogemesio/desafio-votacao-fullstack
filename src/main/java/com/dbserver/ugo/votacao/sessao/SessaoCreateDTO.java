package com.dbserver.ugo.votacao.sessao;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SessaoCreateDTO {

    @NotBlank(message = "O assunto da sessão é obrigatório")
    private String assunto;

}
