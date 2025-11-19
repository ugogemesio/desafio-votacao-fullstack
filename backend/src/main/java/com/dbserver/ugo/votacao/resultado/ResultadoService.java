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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultadoService {

    private final PautaRepository pautaRepository;
    private final VotoRepository votoRepository;
    private final SessaoRepository sessaoRepository;

    @Transactional
    public Resultado calcularResultado(Long sessaoId) {




        Sessao sessao = sessaoRepository.getReferenceById(sessaoId);
        long totalSim = 0;
        long totalNao = 0;
        Pauta pauta = pautaRepository.findById(sessao.getPauta().getId())
                .orElseThrow(() -> new PautaNotFoundException(sessao.getPauta().getId()));


        long sim = votoRepository.countBySessaoIdAndOpcao(sessao.getId(), VotoOpcao.SIM);
        long nao = votoRepository.countBySessaoIdAndOpcao(sessao.getId(), VotoOpcao.NAO);

        totalSim += sim;
        totalNao += nao;


        Resultado resultado = new Resultado(totalSim, totalNao);
        pauta.setResultado(resultado);
        pautaRepository.save(pauta);

        return resultado;
    }
}