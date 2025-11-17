package com.dbserver.ugo.votacao.sessao;


import com.dbserver.ugo.votacao.pauta.Pauta;
import com.dbserver.ugo.votacao.pauta.PautaRepository;
import com.dbserver.ugo.votacao.sessao.exception.SessaoNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessaoService {

    private final SessaoRepository sessaoRepository;
    private final PautaRepository pautaRepository;
    private final SessaoMapper sessaoMapper;

    public SessaoResponseDTO criar(Long pautaId, SessaoCreateDTO dto) {

        Pauta pauta = pautaRepository.findById(pautaId)
                .orElseThrow(() -> new RuntimeException("Pauta não encontrada"));

        Sessao sessao = sessaoMapper.toEntity(dto);

        sessao.setPauta(pauta);
        sessao.setAbertura(LocalDateTime.now());
        sessao.setFechamento(sessao.getAbertura().plusMinutes(dto.duracaoMinutos()));

        sessao = sessaoRepository.save(sessao);

        return sessaoMapper.toDTO(sessao);
    }

    public SessaoResponseDTO buscarPorId(Long idSessao) {
        Sessao sessao = sessaoRepository.findById(idSessao)
                .orElseThrow(() -> new SessaoNotFoundException("Sessão não encontrada"));
        return sessaoMapper.toDTO(sessao);
    }

    public List<SessaoResponseDTO> listar() {
        return sessaoRepository.findAll()
                .stream()
                .map(sessaoMapper::toDTO)
                .toList();
    }
}
