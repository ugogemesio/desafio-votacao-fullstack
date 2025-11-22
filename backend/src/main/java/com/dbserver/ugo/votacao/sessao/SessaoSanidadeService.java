package com.dbserver.ugo.votacao.sessao;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SessaoSanidadeService {

    private static final Logger logger = LoggerFactory.getLogger(SessaoSanidadeService.class);
    private final SessaoRepository sessaoRepository;
    private final SessaoService sessaoService;

    @EventListener(ApplicationReadyEvent.class)
    public void corrigirSessoesBugadas() {
        logger.info("Iniciando verificação de sanidade das sessões na inicialização da aplicação");

        LocalDateTime agora = LocalDateTime.now();
        List<Sessao> sessoesBugadas = sessaoRepository
                .findByStatusAndFechamentoBefore(
                        SessaoStatus.ABERTA,
                        agora
                );

        if (!sessoesBugadas.isEmpty()) {
            logger.warn("Encontradas {} sessões em estado inconsistente na inicialização",
                    sessoesBugadas.size());

            sessoesBugadas.forEach(sessao -> {
                logger.warn("Corrigindo sessão ID: {} - Aberta desde {} mas fechamento era em {}",
                        sessao.getId(), sessao.getAbertura(), sessao.getFechamento());
                try {
                    sessaoService.encerrarSessao(sessao.getId());
                    logger.info("Sessão ID: {} corrigida com sucesso", sessao.getId());
                } catch (Exception e) {
                    logger.error("Erro ao corrigir sessão ID: {}", sessao.getId(), e);
                }
            });

            logger.info("Correção de sessões inconsistentes concluída");
        } else {
            logger.info("Nenhuma sessão inconsistente encontrada na inicialização");
        }
    }
}