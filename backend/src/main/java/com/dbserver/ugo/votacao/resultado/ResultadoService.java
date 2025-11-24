package com.dbserver.ugo.votacao.resultado;

import com.dbserver.ugo.votacao.pauta.Pauta;
import com.dbserver.ugo.votacao.pauta.PautaRepository;
import com.dbserver.ugo.votacao.pauta.exception.PautaNotFoundException;
import com.dbserver.ugo.votacao.sessao.Sessao;
import com.dbserver.ugo.votacao.sessao.SessaoRepository;
import com.dbserver.ugo.votacao.voto.VotoOpcao;
import com.dbserver.ugo.votacao.voto.VotoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResultadoService {

    private static final Logger logger = LoggerFactory.getLogger(ResultadoService.class);
    private final PautaRepository pautaRepository;
    private final VotoRepository votoRepository;
    private final SessaoRepository sessaoRepository;

    @Transactional
    public Resultado calcularResultado(Long sessaoId) {
        logger.info("Calculando resultado para sessão ID: {}", sessaoId);

        Sessao sessao = sessaoRepository.getReferenceById(sessaoId);

//        Pauta pauta = pautaRepository.findById(sessao.getPauta().getId())
//                .orElseThrow(() -> {
//                    logger.error("Pauta não encontrada para sessão ID: {}", sessaoId);
//                    return new PautaNotFoundException(sessao.getPauta().getId());
//                });

        long sim = votoRepository.countBySessaoIdAndOpcao(sessao.getId(), VotoOpcao.SIM);
        long nao = votoRepository.countBySessaoIdAndOpcao(sessao.getId(), VotoOpcao.NAO);

        logger.debug("Resultado da sessão {} - SIM: {}, NÃO: {}", sessaoId, sim, nao);

        Resultado resultado = new Resultado(sim, nao);

//        sessao.setResultado(resultado);
//        sessaoRepository.save(sessao);
      //  pauta.setResultado(resultado);
 //       pautaRepository.save(pauta);

        logger.info("Resultado calculado para sessão {}: {}", sessaoId, resultado.getStatus());
        return resultado;
    }
}