package com.dbserver.ugo.votacao.voto.exception;

public class VotoNotFoundException extends RuntimeException {
    public VotoNotFoundException(String message) {
        super(message);
    }
}
