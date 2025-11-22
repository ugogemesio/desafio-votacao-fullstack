package com.dbserver.ugo.votacao.voto;

import com.dbserver.ugo.votacao.associado.Associado;
import com.dbserver.ugo.votacao.associado.AssociadoRepository;
import com.dbserver.ugo.votacao.associado.exception.AssociadoNotFoundException;
import com.dbserver.ugo.votacao.exceptions.NegocioException;
import com.dbserver.ugo.votacao.sessao.Sessao;
import com.dbserver.ugo.votacao.sessao.SessaoRepository;
import com.dbserver.ugo.votacao.sessao.exception.SessaoNotFoundException;
import com.dbserver.ugo.votacao.voto.exception.VotoNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VotoService {

    private static final Logger logger = LoggerFactory.getLogger(VotoService.class);
    private final VotoRepository votoRepository;
    private final SessaoRepository sessaoRepository;
    private final AssociadoRepository associadoRepository;
    private final VotoMapper votoMapper;

    @Transactional
    public VotoResponseDTO votar(Long sessaoId, String associadoCpf, boolean valor) {
        logger.info("Processando voto - Sessão: {}, CPF: {}, Valor: {}",
                sessaoId, associadoCpf, valor);

        Sessao sessao = sessaoRepository.findById(sessaoId)
                .orElseThrow(() -> {
                    logger.warn("Sessão não encontrada para votação - ID: {}", sessaoId);
                    return new SessaoNotFoundException(sessaoId);
                });

        Associado associado = associadoRepository.findByCpf(associadoCpf)
                .orElseThrow(() -> {
                    logger.warn("Associado não encontrado para votação - CPF: {}", associadoCpf);
                    return new AssociadoNotFoundException(associadoCpf);
                });

        LocalDateTime agora = LocalDateTime.now();
        if (agora.isBefore(sessao.getAbertura()) || agora.isAfter(sessao.getFechamento())) {
            logger.warn("Tentativa de voto fora do horário - Sessão: {}, Horário: {}",
                    sessaoId, agora);
            throw new NegocioException("A sessão não está aberta para votação");
        }

        if (votoRepository.existsByAssociadoIdAndSessaoId(associado.getId(), sessao.getId())) {
            logger.warn("Tentativa de voto duplicado - Sessão: {}, Associado: {}",
                    sessaoId, associado.getId());
            throw new NegocioException("Este associado já votou nesta pauta");
        }

        Voto voto = new Voto();
        voto.setAssociado(associado);
        voto.setSessao(sessao);
        voto.setOpcao(valor ? VotoOpcao.SIM : VotoOpcao.NAO);

        votoRepository.save(voto);
        logger.info("Voto registrado com sucesso - Sessão: {}, Associado: {}, Opção: {}",
                sessaoId, associado.getId(), voto.getOpcao());

        return votoMapper.toDTO(voto);
    }

    public void deletar(Long id) {
        logger.error("Tentativa de deletar voto ID: {}", id);
        throw new NegocioException("Não é permitido excluir um voto.");
    }

    public VotoResponseDTO atualizar(Long id, boolean novoValor) {
        logger.error("Tentativa de atualizar voto ID: {}", id);
        throw new NegocioException("Não é permitido modificar um voto.");
    }

    public VotoResponseDTO buscar(Long id) {
        logger.debug("Buscando voto por ID: {}", id);
        Voto voto = votoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Voto não encontrado - ID: {}", id);
                    return new VotoNotFoundException("Voto não encontrado: " + id);
                });
        return votoMapper.toDTO(voto);
    }
}