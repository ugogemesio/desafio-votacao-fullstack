package com.dbserver.ugo.votacao.pauta;

import com.dbserver.ugo.votacao.exceptions.NegocioException;
import com.dbserver.ugo.votacao.pauta.exception.PautaNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PautaService {

    private static final Logger logger = LoggerFactory.getLogger(PautaService.class);
    private final PautaRepository pautaRepository;
    private final PautaMapper pautaMapper;

    public PautaResponseDTO criar(PautaCreateDTO dto) {
        logger.info("Criando nova pauta: {}", dto.titulo());
        Pauta pauta = pautaMapper.toEntity(dto);
        Pauta saved = pautaRepository.save(pauta);
        logger.debug("Pauta criada com ID: {}", saved.getId());
        return pautaMapper.toDTO(saved);
    }

    public List<PautaResponseDTO> listarPorStatus(PautaStatus status) {
        logger.debug("Listando pautas por status: {}", status);
        List<PautaResponseDTO> result = pautaRepository.findByStatus(status)
                .stream()
                .map(pautaMapper::toDTO)
                .toList();
        logger.debug("Encontradas {} pautas com status {}", result.size(), status);
        return result;
    }

    public PautaResponseDTO atualizarParcial(Long id, PautaPatchDTO dto) {
        logger.info("Atualização parcial da pauta ID: {}", id);
        Pauta entity = pautaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Pauta não encontrada para atualização - ID: {}", id);
                    return new PautaNotFoundException(id);
                });

        pautaMapper.updateFromPatch(dto, entity);
        pautaRepository.save(entity);
        logger.debug("Pauta ID: {} atualizada com status: {}", id, entity.getStatus());
        return pautaMapper.toDTO(entity);
    }

    public PautaResponseDTO buscarPorId(Long id) {
        logger.debug("Buscando pauta por ID: {}", id);
        Pauta entity = pautaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Pauta não encontrada - ID: {}", id);
                    return new PautaNotFoundException(id);
                });
        return pautaMapper.toDTO(entity);
    }

    public List<PautaResponseDTO> listar() {
        logger.debug("Listando todas as pautas");
        List<PautaResponseDTO> result = pautaRepository.findAll()
                .stream()
                .map(pautaMapper::toDTO)
                .toList();
        logger.debug("Encontradas {} pautas", result.size());
        return result;
    }

    public void deletar(Long id) {
        logger.info("Tentativa de deletar pauta ID: {}", id);
        Pauta entity = pautaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Pauta não encontrada para deleção - ID: {}", id);
                    return new PautaNotFoundException(id);
                });

        if (!entity.getSessoes().isEmpty()) {
            logger.warn("Tentativa de excluir pauta com sessões - Pauta ID: {}, Sessões: {}",
                    id, entity.getSessoes().size());
            throw new NegocioException("Não é permitido excluir uma pauta que já possui sessão");
        }

        pautaRepository.delete(entity);
        logger.debug("Pauta ID: {} deletada com sucesso", id);
    }
}