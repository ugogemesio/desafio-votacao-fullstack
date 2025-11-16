package com.dbserver.ugo.votacao.assembleia.exception;

import com.dbserver.ugo.votacao.assembleia.AssembleiaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando uma assembleia não é encontrada no banco.
 * @see AssembleiaService
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class AssembleiaNotFoundException extends RuntimeException {

    public AssembleiaNotFoundException(Long id) {
        super("Assembleia não encontrada: " + id);
    }

    public AssembleiaNotFoundException(String message) {
        super(message);
    }
}
