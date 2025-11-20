package com.dbserver.ugo.votacao.sessao;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
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

    private final ThreadPoolTaskScheduler taskScheduler;
    private final ApplicationEventPublisher eventPublisher;
    private final SessaoRepository sessaoRepository;

    public void agendarEncerramento(Sessao sessao) {

        Date dataExecucao = Date.from(
                sessao.getFechamento()
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
        );

        taskScheduler.schedule(
                () -> eventPublisher.publishEvent(
                        new SessaoEncerramentoEvent(sessao.getId())
                ),
                dataExecucao
        );
    }
    @PostConstruct
    public void iniciarMonitoramentoInicial() {
        monitorarSessoesInconsistentes();
    }

    @Scheduled(fixedDelayString = "${sessao.scheduler.delay:60000}")
    @Transactional
    public void monitorarSessoesInconsistentes() {
        System.out.println(">>> Scheduler executado em " + LocalDateTime.now());
        List<Sessao> inconsistentes = sessaoRepository
                .findByStatusAndFechamentoBefore(
                        SessaoStatus.ABERTA,
                        LocalDateTime.now()
                );

        for (Sessao sessao : inconsistentes) {
            eventPublisher.publishEvent(
                    new SessaoEncerramentoEvent(sessao.getId())
            );
        }
    }
}
