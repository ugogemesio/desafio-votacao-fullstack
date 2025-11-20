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

    @Transactional
    public List<SessaoPautaDTO> listarSessaoPauta() {
        // Busca entidades completas
        List<Sessao> sessoes = sessaoRepository.findAll();


        for (Sessao sessao : sessoes) {
            if (sessao.getStatus().equals(SessaoStatus.ABERTA) &&
                    sessao.getFechamento().isBefore(LocalDateTime.now())) {
                sessao.setStatus(SessaoStatus.ENCERRADA);
            }
        }


        sessaoRepository.saveAll(sessoes);


        return sessoes.stream()
                .map(sessao -> new SessaoPautaDTO(
                        sessao.getId(),
                        sessao.getAbertura(),
                        sessao.getFechamento(),
                        sessao.getDuracaoMinutos(),
                        sessao.getPauta().getId(),
                        sessao.getStatus(),
                        sessao.getPauta().getTitulo(),
                        sessao.getPauta().getDescricao()
                ))
                .toList();
    }


    public SessaoResponseDTO buscarPorId(Long idSessao) {
        return sessaoMapper.toDTO(buscarEntidadePorId(idSessao));
    }

    @Transactional
    public List<SessaoResponseDTO> listar() {
        List<Sessao> sessoes = sessaoRepository.findAll();

        for (Sessao sessao : sessoes) {
            if (sessao.getStatus().equals(SessaoStatus.ABERTA) &&
                    sessao.getFechamento().isBefore(LocalDateTime.now())) {
                sessao.setStatus(SessaoStatus.ENCERRADA);
            }
        }
        sessaoRepository.saveAll(sessoes);

        return sessoes.stream()
                .map(sessaoMapper::toDTO)
                .toList();
    }


    @Transactional
    public SessaoResponseDTO encerrarSessao(Long idSessao) {
        Sessao sessao = buscarEntidadePorId(idSessao);

        sessao.setStatus(SessaoStatus.ENCERRADA);
        sessao.setFechamento(LocalDateTime.now());

        sessao = sessaoRepository.save(sessao);


        Pauta pauta = pautaRepository.getReferenceById(sessao.getPauta().getId());
        Resultado resultado = resultadoService.calcularResultado(sessao.getId());
        pauta.setResultado(resultado);



        if(resultado.getStatus()!= ResultadoStatus.EMPATE){
            pauta.setStatus(PautaStatus.DEFINIDA);
        }else{
            pauta.setStatus(PautaStatus.ABERTA);
        }

        PautaPatchDTO pautaPatchDTO = new PautaPatchDTO(null,null,pauta.getStatus(),resultado);
        pautaService.atualizarParcial(pauta.getId(),pautaPatchDTO);

        return sessaoMapper.toDTO(sessao);
    }


    public void deletar(Long id) {
        throw new NegocioException("Não é permitido excluir uma sessão.");
    }

}
