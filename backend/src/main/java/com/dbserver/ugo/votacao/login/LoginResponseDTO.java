package com.dbserver.ugo.votacao.login;

public record LoginResponseDTO(
        String cpf,
        LoginStatus status
) {}
