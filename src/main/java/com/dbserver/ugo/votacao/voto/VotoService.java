package com.dbserver.ugo.votacao.voto;

import com.dbserver.ugo.votacao.associado.Associado;
import com.dbserver.ugo.votacao.associado.AssociadoRepository;
import com.dbserver.ugo.votacao.sessao.Sessao;
import com.dbserver.ugo.votacao.sessao.SessaoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VotoService {

    private final VotoRepository votoRepository;
    private final AssociadoRepository associadoRepository;
    private final SessaoRepository sessaoRepository;
    private final VotoMapper votoMapper;

    public VotoResponseDTO votar(VotoCreateDTO dto) {

        Associado associado = associadoRepository.findById(dto.getIdAssociado())
                .orElseThrow(() -> new EntityNotFoundException("Associado não encontrado"));

        Sessao sessao = sessaoRepository.findById(dto.getIdSessao())
                .orElseThrow(() -> new EntityNotFoundException("Sessão não encontrada"));

        // REGRA: associado só pode votar 1 vez por sessão
        if (votoRepository.existsByAssociadoIdAssociadoAndSessaoIdSessao(
                dto.getIdAssociado(), dto.getIdSessao())) {
            throw new IllegalArgumentException("Associado já votou nesta sessão");
        }

        Voto voto = new Voto();
        voto.setAssociado(associado);
        voto.setSessao(sessao);
        voto.setValor(dto.getValor());

        Voto saved = votoRepository.save(voto);

        return votoMapper.toDTO(saved);
    }
}
