package com.dbserver.ugo.votacao.sessao;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SessaoStartupReagendador {

    private static final Logger logger = LoggerFactory.getLogger(SessaoStartupReagendador.class);
    private final SessaoRepository sessaoRepository;
    private final SessaoSchedulerService schedulerService;

    @EventListener(ApplicationReadyEvent.class)
    public void reagendarSessoes() {
        logger.info("Iniciando reagendamento de sessões abertas na inicialização da aplicação");

        List<Sessao> abertas = sessaoRepository.findByStatus(SessaoStatus.ABERTA);
        logger.info("Encontradas {} sessões abertas para reagendamento", abertas.size());

        if (!abertas.isEmpty()) {
            abertas.forEach(sessao -> {
                logger.debug("Reagendando encerramento para sessão ID: {} (fechamento: {})",
                        sessao.getId(), sessao.getFechamento());
                schedulerService.agendarEncerramento(sessao);
            });
            logger.info("Reagendamento concluído para {} sessões abertas", abertas.size());
        } else {
            logger.debug("Nenhuma sessão aberta encontrada para reagendamento");
        }
    }
}