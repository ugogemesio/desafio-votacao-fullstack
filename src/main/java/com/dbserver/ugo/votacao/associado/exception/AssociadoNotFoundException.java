package com.dbserver.ugo.votacao.associado.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando um associado não é encontrado no banco.
**/
@ResponseStatus(HttpStatus.NOT_FOUND)
public class AssociadoNotFoundException extends RuntimeException {
    public AssociadoNotFoundException(Long id) {
        super("Associado não encontrado: " + id);
    }

    public AssociadoNotFoundException(String message) {
        super(message);
    }
}
