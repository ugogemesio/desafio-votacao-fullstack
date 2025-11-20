package com.dbserver.ugo.votacao.sessao;

import com.dbserver.ugo.votacao.exceptions.NegocioException;
import com.dbserver.ugo.votacao.pauta.*;
import com.dbserver.ugo.votacao.resultado.Resultado;
import com.dbserver.ugo.votacao.resultado.ResultadoService;
import com.dbserver.ugo.votacao.resultado.ResultadoStatus;
import com.dbserver.ugo.votacao.sessao.exception.SessaoNotFoundException;
import com.dbserver.ugo.votacao.voto.VotoOpcao;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessaoService {

    private final SessaoRepository sessaoRepository;
    private final PautaRepository pautaRepository;
    private final PautaService pautaService;
    private final SessaoMapper sessaoMapper;
    private final ResultadoService resultadoService;
    private final SessaoSchedulerService sessaoSchedulerService;

    public SessaoResponseDTO criar(Long pautaId, SessaoCreateDTO dto) {

        Pauta pauta = pautaRepository.findById(pautaId)
                .orElseThrow(() -> new SessaoNotFoundException("Pauta não encontrada"));

        if (pauta.getStatus().equals(PautaStatus.VOTANDO) || pauta.getStatus().equals(PautaStatus.DEFINIDA)) {
            throw new NegocioException("Não é possível criar pauta que está " + pauta.getStatus().toString().toLowerCase());
        }
        pauta.setStatus(PautaStatus.VOTANDO);

        Sessao sessao = sessaoMapper.toEntity(dto);
        sessao.setPauta(pauta);

        sessao.setAbertura(LocalDateTime.now());
        int duracao = dto.duracaoMinutos() == null ? 1 : dto.duracaoMinutos();
        sessao.setDuracaoMinutos(duracao);
        sessao.setFechamento(sessao.getAbertura().plusMinutes(duracao));
        sessao.setStatus(SessaoStatus.ABERTA);

        sessao = sessaoRepository.save(sessao);


        sessaoSchedulerService.agendarEncerramento(sessao);

        return sessaoMapper.toDTO(sessao);
    }

    public Sessao buscarEntidadePorId(Long idSessao) {
        return sessaoRepository.findById(idSessao)
                .orElseThrow(() -> new SessaoNotFoundException("Sessão não encontrada"));
    }
    //mapear
    public List<SessaoPautaDTO> listarSessaoPauta() {
        return sessaoRepository.findAllWithPauta();
    }

    public SessaoResponseDTO buscarPorId(Long idSessao) {
        return sessaoMapper.toDTO(buscarEntidadePorId(idSessao));
    }

    public List<SessaoResponseDTO> listar() {
        return sessaoRepository.findAll()
                .stream()
                .map(sessaoMapper::toDTO)
                .toList();
    }


    @Transactional
    public SessaoResponseDTO encerrarSessao(Long idSessao) {
        Sessao sessao = buscarEntidadePorId(idSessao);

        if (sessao.getStatus() == SessaoStatus.ENCERRADA) {
            return sessaoMapper.toDTO(sessao);
        }

        sessao.setStatus(SessaoStatus.ENCERRADA);
        sessao.setFechamento(LocalDateTime.now());
        sessaoRepository.save(sessao);

        Pauta pauta = pautaRepository.getReferenceById(sessao.getPauta().getId());
        Resultado resultado = resultadoService.calcularResultado(sessao.getId());
        pauta.setResultado(resultado);

        if(resultado.getStatus() != ResultadoStatus.EMPATE){
            pauta.setStatus(PautaStatus.DEFINIDA);
        } else {
            pauta.setStatus(PautaStatus.ABERTA);
        }

        PautaPatchDTO dto = new PautaPatchDTO(null, null, pauta.getStatus(), resultado);
        pautaService.atualizarParcial(pauta.getId(), dto);

        return sessaoMapper.toDTO(sessao);
    }


    public void deletar(Long id) {
        throw new NegocioException("Não é permitido excluir uma sessão.");
    }

}
