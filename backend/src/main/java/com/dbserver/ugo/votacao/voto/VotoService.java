package com.dbserver.ugo.votacao.voto;

import com.dbserver.ugo.votacao.associado.Associado;
import com.dbserver.ugo.votacao.associado.AssociadoRepository;
import com.dbserver.ugo.votacao.exceptions.NegocioException;
import com.dbserver.ugo.votacao.sessao.Sessao;
import com.dbserver.ugo.votacao.sessao.SessaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VotoService {

    private final VotoRepository votoRepository;
    private final SessaoRepository sessaoRepository;
    private final AssociadoRepository associadoRepository;
    private final VotoMapper votoMapper;

    @Transactional
    public VotoResponseDTO votar(Long sessaoId, String associadoCpf, boolean valor) {

        Sessao sessao = sessaoRepository.findById(sessaoId)
                .orElseThrow(() -> new NegocioException("Sessão não encontrada"));

        Associado associado = associadoRepository.findByCpf(associadoCpf)
                .orElseThrow(() -> new NegocioException("Associado não encontrado"));

        LocalDateTime agora = LocalDateTime.now();
        if (agora.isBefore(sessao.getAbertura()) || agora.isAfter(sessao.getFechamento())) {
            throw new NegocioException("A sessão não está aberta para votação");
        }

        if (votoRepository.existsByAssociadoIdAndSessaoId(associado.getId(), sessao.getId())) {
            throw new NegocioException("Este associado já votou nesta pauta");
        }

        Voto voto = new Voto();
        voto.setAssociado(associado);
        voto.setSessao(sessao);
        voto.setOpcao(valor ? VotoOpcao.SIM : VotoOpcao.NAO);

        votoRepository.save(voto);
        return votoMapper.toDTO(voto);
    }
    public void deletar(Long id) {
        throw new NegocioException("Não é permitido excluir um voto.");
    }

    public VotoResponseDTO atualizar(Long id, boolean novoValor) {
        throw new NegocioException("Não é permitido modificar um voto.");
    }


    public Optional<Voto> buscarEntidade(Long id) {
        return votoRepository.findById(id);
    }

    public Optional<VotoResponseDTO> buscar(Long id) {
        return votoRepository.findById(id).map(votoMapper::toDTO);
    }
}
