package com.dbserver.ugo.votacao.pauta;

import com.dbserver.ugo.votacao.assembleia.Assembleia;
import com.dbserver.ugo.votacao.assembleia.AssembleiaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PautaService {

    private final PautaRepository pautaRepository;
    private final AssembleiaRepository assembleiaRepository;
    private final PautaMapper pautaMapper;

    public PautaResponseDTO criar(Long idAssembleia, PautaCreateDTO dto) {

        Assembleia assembleia = assembleiaRepository.findById(idAssembleia)
                .orElseThrow(() -> new EntityNotFoundException("Assembleia não encontrada"));

        Pauta pauta = pautaMapper.toEntity(dto);
        pauta.setAssembleia(assembleia);

        Pauta saved = pautaRepository.save(pauta);
        return pautaMapper.toDTO(saved);
    }

    public PautaResponseDTO atualizar(Long idPauta, PautaUpdateDTO dto) {
        Pauta entity = pautaRepository.findById(idPauta)
                .orElseThrow(() -> new EntityNotFoundException("Pauta não encontrada"));

        pautaMapper.updateEntityFromDTO(dto, entity);
        pautaRepository.save(entity);

        return pautaMapper.toDTO(entity);
    }

    public void deletar(Long idPauta) {
        if (!pautaRepository.existsById(idPauta)) {
            throw new EntityNotFoundException("Pauta não encontrada");
        }
        pautaRepository.deleteById(idPauta);
    }

    public PautaResponseDTO buscarPorId(Long idPauta) {
        Pauta entity = pautaRepository.findById(idPauta)
                .orElseThrow(() -> new EntityNotFoundException("Pauta não encontrada"));
        return pautaMapper.toDTO(entity);
    }

    public List<PautaResponseDTO> listar() {
        return pautaRepository.findAll()
                .stream()
                .map(pautaMapper::toDTO)
                .toList();
    }
}
