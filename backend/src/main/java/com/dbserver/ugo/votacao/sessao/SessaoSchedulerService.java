package com.dbserver.ugo.votacao.sessao;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class SessaoSchedulerService {

    private final ThreadPoolTaskScheduler taskScheduler;
    private final ApplicationEventPublisher eventPublisher;

    public void agendarEncerramento(Sessao sessao) {

        long delayMillis = sessao.getDuracaoMinutos() * 60L * 1000L;
        Date dataExecucao = new Date(System.currentTimeMillis() + delayMillis);

        taskScheduler.schedule(
                () -> eventPublisher.publishEvent(new SessaoEncerramentoEvent(sessao.getId())),
                dataExecucao
        );
    }
}
