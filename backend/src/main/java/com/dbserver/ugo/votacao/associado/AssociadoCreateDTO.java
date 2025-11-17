package com.dbserver.ugo.votacao.associado;

import com.dbserver.ugo.votacao.utils.CpfValido;
import jakarta.validation.constraints.NotBlank;

public record AssociadoCreateDTO(
    @NotBlank(message = "Nome do associado é obrigatório")
    String nome,

    @NotBlank(message = "CPF do associado é obrigatório")
    @CpfValido(message = "Tamnanho deve ser de 11 carácteres")
    String cpf
){}
