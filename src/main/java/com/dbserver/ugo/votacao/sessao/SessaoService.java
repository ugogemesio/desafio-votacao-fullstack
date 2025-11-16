package com.dbserver.ugo.votacao.sessao;

import com.dbserver.ugo.votacao.pauta.Pauta;
import com.dbserver.ugo.votacao.pauta.PautaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessaoService {

    private final SessaoRepository sessaoRepository;
    private final PautaRepository pautaRepository;
    private final SessaoMapper sessaoMapper;

    public SessaoResponseDTO criar(Long idPauta, SessaoCreateDTO dto) {

        Pauta pauta = pautaRepository.findById(idPauta)
                .orElseThrow(() -> new EntityNotFoundException("Pauta não encontrada"));

        Sessao sessao = sessaoMapper.toEntity(dto);
        sessao.setPauta(pauta);

        Sessao saved = sessaoRepository.save(sessao);

        return sessaoMapper.toDTO(saved);
    }

    public SessaoResponseDTO atualizar(Long idSessao, SessaoUpdateDTO dto) {
        Sessao sessao = sessaoRepository.findById(idSessao)
                .orElseThrow(() -> new EntityNotFoundException("Sessão não encontrada"));

        sessaoMapper.updateEntityFromDTO(dto, sessao);
        sessaoRepository.save(sessao);

        return sessaoMapper.toDTO(sessao);
    }

    public SessaoResponseDTO buscarPorId(Long idSessao) {
        Sessao sessao = sessaoRepository.findById(idSessao)
                .orElseThrow(() -> new EntityNotFoundException("Sessão não encontrada"));
        return sessaoMapper.toDTO(sessao);
    }

    public List<SessaoResponseDTO> listar() {
        return sessaoRepository.findAll()
                .stream()
                .map(sessaoMapper::toDTO)
                .toList();
    }

    public void deletar(Long idSessao) {
        if (!sessaoRepository.existsById(idSessao)) {
            throw new EntityNotFoundException("Sessão não encontrada");
        }
        sessaoRepository.deleteById(idSessao);
    }
}
