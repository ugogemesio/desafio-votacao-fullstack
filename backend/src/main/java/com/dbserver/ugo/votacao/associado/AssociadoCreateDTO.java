package com.dbserver.ugo.votacao.associado;

import jakarta.validation.constraints.NotBlank;

public record AssociadoCreateDTO(
    @NotBlank(message = "Nome do associado é obrigatório")
    String nome,
    @NotBlank(message = "CPF do associado é obrigatório")
    String cpf
){}
