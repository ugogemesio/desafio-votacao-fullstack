package com.dbserver.ugo.votacao.associado;

import com.dbserver.ugo.votacao.associado.exception.AssociadoNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssociadoService {

    private final AssociadoRepository repository;
    private final AssociadoMapper mapper;

    public AssociadoResponseDTO criar(AssociadoCreateDTO dto) {
        Associado entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    public AssociadoResponseDTO buscarPorId(Long id) {
        Associado entity = repository.findById(id)
                .orElseThrow(() -> new AssociadoNotFoundException(id));
        return mapper.toDTO(entity);
    }

    public AssociadoResponseDTO buscarPorCpf(String cpf) {
        Associado entity = repository.findByCpf(cpf)
                .orElseThrow(() -> new AssociadoNotFoundException(cpf));
        return mapper.toDTO(entity);
    }

    public List<AssociadoResponseDTO> listar() {
        return mapper.toDTOList(repository.findAll());
    }

    public AssociadoResponseDTO atualizar(Long id, AssociadoPutDTO dto) {
        Associado entity = repository.findById(id)
                .orElseThrow(() -> new AssociadoNotFoundException(id));

        mapper.updateFromPut(dto, entity);

        return mapper.toDTO(repository.save(entity));
    }


    public AssociadoResponseDTO atualizarParcial(Long id, AssociadoPatchDTO dto) {
        Associado entity = repository.findById(id)
                .orElseThrow(() -> new AssociadoNotFoundException(id));

        mapper.updateFromPatch(dto, entity);

        return mapper.toDTO(repository.save(entity));
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new AssociadoNotFoundException(id);
        }
        repository.deleteById(id);
    }
}
