package com.dbserver.ugo.votacao.sessao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando uma sessao não é encontrado no banco.
 **/
@ResponseStatus(HttpStatus.NOT_FOUND)
public class SessaoNotFoundException extends RuntimeException {
    public SessaoNotFoundException(Long id){
        super("Sessão não encontrada: "+ id);
    }
    public SessaoNotFoundException(String message){
        super(message);
    }
}
