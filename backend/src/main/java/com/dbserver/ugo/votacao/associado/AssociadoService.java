package com.dbserver.ugo.votacao.associado;

import com.dbserver.ugo.votacao.associado.exception.AssociadoNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssociadoService {

    private static final Logger logger = LoggerFactory.getLogger(AssociadoService.class);
    private final AssociadoRepository repository;
    private final AssociadoMapper mapper;

    public AssociadoResponseDTO criar(AssociadoCreateDTO dto) {
        logger.info("Criando novo associado com CPF: {}", dto.cpf());
        Associado entity = mapper.toEntity(dto);
        Associado saved = repository.save(entity);
        logger.debug("Associado criado com ID: {}", saved.getId());
        return mapper.toDTO(saved);
    }

    public AssociadoResponseDTO buscarPorId(Long id) {
        logger.debug("Buscando associado por ID: {}", id);
        Associado entity = repository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Associado não encontrado com ID: {}", id);
                    return new AssociadoNotFoundException(id);
                });
        return mapper.toDTO(entity);
    }

    public AssociadoResponseDTO buscarPorCpf(String cpf) {
        logger.debug("Buscando associado por CPF: {}", cpf);
        Associado entity = repository.findByCpf(cpf)
                .orElseThrow(() -> {
                    logger.warn("Associado não encontrado com CPF: {}", cpf);
                    return new AssociadoNotFoundException(cpf);
                });
        return mapper.toDTO(entity);
    }

    public List<AssociadoResponseDTO> listar() {
        logger.debug("Listando todos os associados");
        List<AssociadoResponseDTO> result = mapper.toDTOList(repository.findAll());
        logger.debug("Encontrados {} associados", result.size());
        return result;
    }

    public AssociadoResponseDTO atualizar(Long id, AssociadoPutDTO dto) {
        logger.info("Atualizando associado ID: {}", id);
        Associado entity = repository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Associado não encontrado para atualização - ID: {}", id);
                    return new AssociadoNotFoundException(id);
                });

        mapper.updateFromPut(dto, entity);
        Associado updated = repository.save(entity);
        logger.debug("Associado ID: {} atualizado com sucesso", id);
        return mapper.toDTO(updated);
    }

    public AssociadoResponseDTO atualizarParcial(Long id, AssociadoPatchDTO dto) {
        logger.info("Atualização parcial do associado ID: {}", id);
        Associado entity = repository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Associado não encontrado para atualização parcial - ID: {}", id);
                    return new AssociadoNotFoundException(id);
                });

        mapper.updateFromPatch(dto, entity);
        Associado updated = repository.save(entity);
        logger.debug("Associado ID: {} atualizado parcialmente com sucesso", id);
        return mapper.toDTO(updated);
    }

    public void deletar(Long id) {
        logger.info("Deletando associado ID: {}", id);
        if (!repository.existsById(id)) {
            logger.warn("Tentativa de deletar associado inexistente - ID: {}", id);
            throw new AssociadoNotFoundException(id);
        }
        repository.deleteById(id);
        logger.debug("Associado ID: {} deletado com sucesso", id);
    }
}