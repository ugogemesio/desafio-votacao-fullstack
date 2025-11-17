package com.dbserver.ugo.votacao.pauta;

import jakarta.validation.constraints.NotBlank;


public record PautaCreateDTO (

    @NotBlank(message = "A descrição da pauta é obrigatório")
    String descricao,

    @NotBlank(message = "o título da pauta é obrigatório")
    String titulo

){}
