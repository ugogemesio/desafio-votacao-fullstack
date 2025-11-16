package com.dbserver.ugo.votacao.assembleia;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssembleiaService {

    private final AssembleiaRepository assembleiaRepository;
    private final AssembleiaMapper assembleiaMapper;

    public List<AssembleiaResponseDTO> findByNomeAssembleia(String nomeAssembleia) {
        return assembleiaRepository.findByNomeAssembleia(nomeAssembleia)
                .stream()
                .map(assembleiaMapper::toDTO)
                .toList();
    }

    public List<AssembleiaResponseDTO> findAfter(LocalDateTime data) {
        return assembleiaRepository.findByDataRealizacaoAssembleiaAfter(data)
                .stream()
                .map(assembleiaMapper::toDTO)
                .toList();
    }

    public List<AssembleiaResponseDTO> findBefore(LocalDateTime data) {
        return assembleiaRepository.findByDataRealizacaoAssembleiaBefore(data)
                .stream()
                .map(assembleiaMapper::toDTO)
                .toList();
    }

    public List<AssembleiaResponseDTO> findBetween(LocalDateTime inicio, LocalDateTime fim) {
        return assembleiaRepository.findByDataRealizacaoAssembleiaBetween(inicio, fim)
                .stream()
                .map(assembleiaMapper::toDTO)
                .toList();
    }

    public AssembleiaResponseDTO findById(Long id) {
        Assembleia entity = assembleiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assembleia não encontrada"));

        return assembleiaMapper.toDTO(entity);
    }

    public AssembleiaResponseDTO criarAssembleia(Assembleia entity) {
        Assembleia saved = assembleiaRepository.save(entity);
        return assembleiaMapper.toDTO(saved);
    }

    public void deletarAssembleia(Long id) {
        if (!assembleiaRepository.existsById(id)) {
            throw new RuntimeException("Assembleia não encontrada");
        }
        assembleiaRepository.deleteById(id);
    }
}
