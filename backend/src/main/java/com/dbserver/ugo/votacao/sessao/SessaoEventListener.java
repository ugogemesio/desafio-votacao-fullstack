package com.dbserver.ugo.votacao.sessao;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessaoEventListener {

    private static final Logger logger = LoggerFactory.getLogger(SessaoEventListener.class);
    private final SessaoService sessaoService;

    @EventListener
    public void handleSessaoEncerramentoEvent(SessaoEncerramentoEvent event) {
        logger.info("Processando evento de encerramento para sessão ID: {}", event.sessaoId());

        try {
            sessaoService.encerrarSessao(event.sessaoId());
            logger.info("Evento de encerramento processado com sucesso para sessão ID: {}", event.sessaoId());
        } catch (Exception e) {
            logger.error("Erro ao processar evento de encerramento para sessão ID: {}", event.sessaoId(), e);

            throw e;
        }
    }
}