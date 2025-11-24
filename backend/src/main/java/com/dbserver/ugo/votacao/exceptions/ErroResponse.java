package com.dbserver.ugo.votacao.exceptions;

import java.time.LocalDateTime;

public record ErroResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {}
