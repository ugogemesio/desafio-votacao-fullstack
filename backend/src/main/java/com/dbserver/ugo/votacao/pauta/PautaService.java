package com.dbserver.ugo.votacao.pauta;

import com.dbserver.ugo.votacao.exceptions.NegocioException;
import com.dbserver.ugo.votacao.pauta.exception.PautaNotFoundException;
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

    public List<PautaResponseDTO> listarPorStatus(PautaStatus status) {
        return pautaRepository.findByStatus(status)
                .stream()
                .map(pautaMapper::toDTO) // usar MapStruct ou manualmente
                .toList();
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
        Pauta entity = pautaRepository.findById(idPauta)
                .orElseThrow(() -> new PautaNotFoundException(idPauta));
        if (!entity.getSessoes().isEmpty()) {
            throw new NegocioException("Não é permitido excluir uma pauta que já possui sessão");
        }
        pautaRepository.delete(entity);
    }
}
