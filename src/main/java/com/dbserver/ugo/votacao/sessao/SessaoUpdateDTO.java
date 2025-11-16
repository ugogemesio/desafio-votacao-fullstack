package com.dbserver.ugo.votacao.sessao;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SessaoUpdateDTO {
    @NotBlank(message = "Assunto da sessão é obrigatório")
    private String assunto;
}
