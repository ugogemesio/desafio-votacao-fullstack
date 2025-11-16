package com.dbserver.ugo.votacao.assembleia;

import com.dbserver.ugo.votacao.assembleia.exception.AssembleiaNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class AssembleiaService {

    private final AssembleiaRepository assembleiaRepository;
    private final AssembleiaMapper assembleiaMapper;

    public AssembleiaResponseDTO criar(AssembleiaCreateDTO dto) {
        Assembleia entity = assembleiaMapper.toEntity(dto);
        Assembleia saved = assembleiaRepository.save(entity);
        return assembleiaMapper.toDTO(saved);
    }

    public AssembleiaResponseDTO atualizar(Long id, AssembleiaUpdateDTO dto) {
        Assembleia entity = assembleiaRepository.findById(id)
                .orElseThrow(() -> new AssembleiaNotFoundException(id));

        assembleiaMapper.updateEntityFromDTO(dto, entity);
        assembleiaRepository.save(entity);

        return assembleiaMapper.toDTO(entity);
    }

    public void deletar(Long id) {
        Assembleia entity = assembleiaRepository.findById(id)
                .orElseThrow(() -> new AssembleiaNotFoundException(id));
        assembleiaRepository.delete(entity);
    }

    public List<AssembleiaResponseDTO> buscarPorNome(String nomeAssembleia) {
        return assembleiaRepository.findByNomeAssembleia(nomeAssembleia)
                .stream()
                .map(assembleiaMapper::toDTO)
                .toList();
    }

    public AssembleiaResponseDTO buscarPorId(Long id) {
        Assembleia entity = assembleiaRepository.findById(id)
                .orElseThrow(() -> new AssembleiaNotFoundException(id));
        return assembleiaMapper.toDTO(entity);
    }


}
