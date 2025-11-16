package com.dbserver.ugo.votacao.associado;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssociadoService {

    private final AssociadoRepository associadoRepository;
    private final AssociadoMapper associadoMapper;

    public AssociadoResponseDTO criar(AssociadoCreateDTO dto) {
        Associado entity = associadoMapper.toEntity(dto);
        Associado saved = associadoRepository.save(entity);
        return associadoMapper.toDTO(saved);
    }

    public AssociadoResponseDTO buscarPorId(Long id) {
        Associado entity = associadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Associado não encontrado"));
        return associadoMapper.toDTO(entity);
    }

    public List<AssociadoResponseDTO> listar() {
        return associadoMapper.toDTOList(associadoRepository.findAll());
    }

    public AssociadoResponseDTO atualizar(Long id, AssociadoUpdateDTO dto) {
        Associado entity = associadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Associado não encontrado"));

        associadoMapper.updateEntityFromDTO(dto, entity);
        associadoRepository.save(entity);

        return associadoMapper.toDTO(entity);
    }

    public void deletar(Long id) {
        if (!associadoRepository.existsById(id)) {
            throw new RuntimeException("Associado não encontrado");
        }
        associadoRepository.deleteById(id);
    }
}
