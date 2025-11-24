package com.dbserver.ugo.votacao.exceptions;

import com.dbserver.ugo.votacao.associado.exception.AssociadoNotFoundException;
import com.dbserver.ugo.votacao.pauta.exception.PautaNotFoundException;
import com.dbserver.ugo.votacao.sessao.exception.SessaoNotFoundException;
import com.dbserver.ugo.votacao.voto.exception.VotoNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({
            PautaNotFoundException.class,
            SessaoNotFoundException.class,
            AssociadoNotFoundException.class,
            VotoNotFoundException.class
    })
    public ResponseEntity<ErroResponse> handleNotFound(
            RuntimeException ex,
            HttpServletRequest request) {

        logger.warn("Recurso não encontrado - Path: {}, Mensagem: {}",
                request.getRequestURI(), ex.getMessage());

        return buildResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<ErroResponse> handleNegocioException(
            NegocioException ex,
            HttpServletRequest request) {

        logger.warn("Violação de regra de negócio - Path: {}, Mensagem: {}",
                request.getRequestURI(), ex.getMessage());

        return buildResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        logger.error("Erro interno não esperado - Path: {}, Erro: ",
                request.getRequestURI(), ex);

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocorreu um erro inesperado. Tente novamente mais tarde.",
                request.getRequestURI()
        );
    }

    private ResponseEntity<ErroResponse> buildResponse(
            HttpStatus status,
            String message,
            String path) {

        ErroResponse body = new ErroResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                path
        );

        return ResponseEntity.status(status).body(body);
    }
}