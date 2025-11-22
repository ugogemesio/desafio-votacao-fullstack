package com.dbserver.ugo.votacao.sessao;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessaoSchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(SessaoSchedulerService.class);
    private final ThreadPoolTaskScheduler taskScheduler;
    private final ApplicationEventPublisher eventPublisher;
    private final SessaoRepository sessaoRepository;

    public void agendarEncerramento(Sessao sessao) {
        try {
            logger.debug("Agendando encerramento automático para sessão ID: {} às {}",
                    sessao.getId(), sessao.getFechamento());

            Date dataExecucao = Date.from(
                    sessao.getFechamento()
                            .atZone(ZoneId.systemDefault())
                            .toInstant()
            );

            taskScheduler.schedule(
                    () -> {
                        logger.info("Disparando encerramento automático para sessão ID: {}", sessao.getId());
                        eventPublisher.publishEvent(
                                new SessaoEncerramentoEvent(sessao.getId())
                        );
                    },
                    dataExecucao
            );

            logger.debug("Encerramento agendado com sucesso para sessão ID: {} em {}",
                    sessao.getId(), dataExecucao);

        } catch (Exception e) {
            logger.error("Erro ao agendar encerramento para sessão ID: {}", sessao.getId(), e);
            throw e;
        }
    }

    @PostConstruct
    public void iniciarMonitoramentoInicial() {
        logger.info("Iniciando monitoramento de sessões inconsistentes");
        monitorarSessoesInconsistentes();
    }

    @Scheduled(fixedDelayString = "${sessao.scheduler.delay:60000}")
    @Transactional
    public void monitorarSessoesInconsistentes() {
        logger.debug("Executando verificação de sessões inconsistentes");

        LocalDateTime agora = LocalDateTime.now();
        List<Sessao> inconsistentes = sessaoRepository
                .findByStatusAndFechamentoBefore(
                        SessaoStatus.ABERTA,
                        agora
                );

        if (!inconsistentes.isEmpty()) {
            logger.warn("Encontradas {} sessões inconsistentes (abertas após o fechamento)",
                    inconsistentes.size());

            for (Sessao sessao : inconsistentes) {
                logger.warn("Sessão ID: {} está aberta mas deveria ter fechado em {}. Disparando encerramento.",
                        sessao.getId(), sessao.getFechamento());
                eventPublisher.publishEvent(
                        new SessaoEncerramentoEvent(sessao.getId())
                );
            }
        } else {
            logger.debug("Nenhuma sessão inconsistente encontrada na verificação às {}", agora);
        }
    }
}