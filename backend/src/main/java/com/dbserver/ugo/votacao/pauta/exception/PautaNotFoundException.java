package com.dbserver.ugo.votacao.pauta.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando uma pauta não é encontrado no banco.
 **/
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PautaNotFoundException extends RuntimeException {
    public PautaNotFoundException(Long id) {
        super("Pauta não encontrada: "+ id);
    }
    public PautaNotFoundException(String message) {
        super(message);
    }
}
