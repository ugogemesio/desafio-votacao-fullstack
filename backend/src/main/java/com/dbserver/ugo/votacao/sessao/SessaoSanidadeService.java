package com.dbserver.ugo.votacao.sessao;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SessaoSanidadeService {

    private final SessaoRepository sessaoRepository;
    private final SessaoService sessaoService;

    @EventListener(ApplicationReadyEvent.class)
    public void corrigirSessoesBugadas() {

        List<Sessao> sessoesBugadas = sessaoRepository
                .findByStatusAndFechamentoBefore(
                        SessaoStatus.ABERTA,
                        LocalDateTime.now()
                );

        sessoesBugadas.forEach(sessao ->
                sessaoService.encerrarSessao(sessao.getId())
        );
    }
}

