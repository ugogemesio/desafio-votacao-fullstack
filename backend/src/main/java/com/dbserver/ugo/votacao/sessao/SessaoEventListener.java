package com.dbserver.ugo.votacao.sessao;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessaoEventListener {

    private final SessaoService sessaoService;

    @EventListener
    public void handleSessaoEncerramentoEvent(SessaoEncerramentoEvent event) {
        System.out.print("deb");
        sessaoService.encerrarSessao(event.sessaoId());
    }
}
