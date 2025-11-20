package com.dbserver.ugo.votacao.sessao;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SessaoStartupReagendador {

    private final SessaoRepository sessaoRepository;
    private final SessaoSchedulerService schedulerService;

    @EventListener(ApplicationReadyEvent.class)
    public void reagendarSessoes() {
        List<Sessao> abertas = sessaoRepository.findByStatus(SessaoStatus.ABERTA);

        abertas.forEach(schedulerService::agendarEncerramento);
    }
}
