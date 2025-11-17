package com.dbserver.ugo.votacao.pauta;

import com.dbserver.ugo.votacao.pauta.exception.PautaNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PautaService {

    private final PautaRepository pautaRepository;
    private final PautaMapper pautaMapper;


    public PautaResponseDTO criar(PautaCreateDTO dto) {
        Pauta pauta = pautaMapper.toEntity(dto);
        Pauta saved = pautaRepository.save(pauta);
        return pautaMapper.toDTO(saved);
    }


    public PautaResponseDTO atualizar(Long idPauta, PautaPutDTO dto) {
        Pauta entity = pautaRepository.findById(idPauta)
                .orElseThrow(() -> new PautaNotFoundException(idPauta));
        pautaMapper.updateFromPut(dto, entity);
        return pautaMapper.toDTO(pautaRepository.save(entity));
    }



    public PautaResponseDTO atualizarParcial(Long idPauta, PautaPatchDTO dto) {
        Pauta entity = pautaRepository.findById(idPauta)
                .orElseThrow(() -> new PautaNotFoundException(idPauta));
        pautaMapper.updateFromPatch(dto, entity);
        pautaRepository.save(entity);
        return pautaMapper.toDTO(entity);
    }


    public PautaResponseDTO buscarPorId(Long idPauta) {
        Pauta entity = pautaRepository.findById(idPauta)
                .orElseThrow(() -> new PautaNotFoundException(idPauta));
        return pautaMapper.toDTO(entity);
    }

    public List<PautaResponseDTO> listar() {
        return pautaRepository.findAll()
                .stream()
                .map(pautaMapper::toDTO)
                .toList();
    }

    public void deletar(Long idPauta) {
        if (!pautaRepository.existsById(idPauta)) {
            throw new PautaNotFoundException(idPauta);
        }
        pautaRepository.deleteById(idPauta);
    }

}
