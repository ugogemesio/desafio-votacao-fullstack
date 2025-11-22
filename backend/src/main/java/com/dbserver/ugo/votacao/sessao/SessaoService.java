package com.dbserver.ugo.votacao.sessao;

import com.dbserver.ugo.votacao.exceptions.NegocioException;
import com.dbserver.ugo.votacao.pauta.*;
import com.dbserver.ugo.votacao.pauta.exception.PautaNotFoundException;
import com.dbserver.ugo.votacao.resultado.Resultado;
import com.dbserver.ugo.votacao.resultado.ResultadoService;
import com.dbserver.ugo.votacao.resultado.ResultadoStatus;
import com.dbserver.ugo.votacao.sessao.exception.SessaoNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessaoService {

    private static final Logger logger = LoggerFactory.getLogger(SessaoService.class);
    private final SessaoRepository sessaoRepository;
    private final PautaRepository pautaRepository;
    private final PautaService pautaService;
    private final SessaoMapper sessaoMapper;
    private final ResultadoService resultadoService;
    private final SessaoSchedulerService sessaoSchedulerService;

    public SessaoResponseDTO criar(Long pautaId, SessaoCreateDTO dto) {
        logger.info("Criando sessão para pauta ID: {}", pautaId);

        Pauta pauta = pautaRepository.findById(pautaId)
                .orElseThrow(() -> {
                    logger.warn("Pauta não encontrada para criar sessão - ID: {}", pautaId);
                    return new PautaNotFoundException(pautaId);
                });

        if (pauta.getStatus().equals(PautaStatus.VOTANDO) || pauta.getStatus().equals(PautaStatus.DEFINIDA)) {
            logger.warn("Tentativa de criar sessão para pauta com status inválido - Pauta ID: {}, Status: {}",
                    pautaId, pauta.getStatus());
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
        logger.debug("Sessão criada com ID: {} para pauta ID: {}", sessao.getId(), pautaId);

        sessaoSchedulerService.agendarEncerramento(sessao);
        logger.info("Encerramento agendado para sessão ID: {}", sessao.getId());

        return sessaoMapper.toDTO(sessao);
    }

    public Sessao buscarEntidadePorId(Long id) {
        logger.debug("Buscando entidade sessão por ID: {}", id);
        return sessaoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Sessão não encontrada - ID: {}", id);
                    return new SessaoNotFoundException(id);
                });
    }

    public List<SessaoPautaDTO> listarComPauta() {
        logger.debug("Listando sessões com informações de pauta");
        List<SessaoPautaDTO> result = sessaoRepository.findAllWithPauta().stream()
                .map(this::toDTO)
                .toList();
        logger.debug("Encontradas {} sessões", result.size());
        return result;
    }

    private SessaoPautaDTO toDTO(Sessao sessao) {
        return new SessaoPautaDTO(
                sessao.getId(),
                sessao.getAbertura(),
                sessao.getFechamento(),
                sessao.getDuracaoMinutos(),
                sessao.getPauta().getId(),
                sessao.getStatus(),
                sessao.getPauta().getTitulo(),
                sessao.getPauta().getDescricao()
        );
    }

    public SessaoResponseDTO buscarPorId(Long id) {
        logger.debug("Buscando sessão por ID: {}", id);
        return sessaoMapper.toDTO(buscarEntidadePorId(id));
    }

    public List<SessaoResponseDTO> listar() {
        logger.debug("Listando todas as sessões");
        List<SessaoResponseDTO> result = sessaoRepository.findAll()
                .stream()
                .map(sessaoMapper::toDTO)
                .toList();
        logger.debug("Encontradas {} sessões", result.size());
        return result;
    }

    @Transactional
    public SessaoResponseDTO encerrarSessao(Long id) {
        logger.info("Encerrando sessão ID: {}", id);
        Sessao sessao = buscarEntidadePorId(id);

        if (sessao.getStatus() == SessaoStatus.ENCERRADA) {
            logger.debug("Sessão ID: {} já está encerrada", id);
            return sessaoMapper.toDTO(sessao);
        }

        sessao.setStatus(SessaoStatus.ENCERRADA);
        sessao.setFechamento(LocalDateTime.now());
        sessaoRepository.save(sessao);
        logger.debug("Sessão ID: {} marcada como encerrada", id);

        Pauta pauta = pautaRepository.getReferenceById(sessao.getPauta().getId());
        Resultado resultado = resultadoService.calcularResultado(sessao.getId());
        pauta.setResultado(resultado);

        if(resultado.getStatus() != ResultadoStatus.EMPATE){
            pauta.setStatus(PautaStatus.DEFINIDA);
        } else {
            pauta.setStatus(PautaStatus.ABERTA);
        }

        PautaPatchDTO patchDTO = new PautaPatchDTO(null, null, pauta.getStatus(), resultado);
        pautaService.atualizarParcial(pauta.getId(), patchDTO);

        logger.info("Sessão ID: {} encerrada. Resultado: {}", id, resultado.getStatus());
        return sessaoMapper.toDTO(sessao);
    }

    public void deletar(Long id) {
        logger.error("Tentativa de deletar sessão ID: {}", id);
        throw new NegocioException("Não é permitido excluir uma sessão.");
    }
}