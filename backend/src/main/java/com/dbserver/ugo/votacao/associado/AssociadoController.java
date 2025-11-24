package com.dbserver.ugo.votacao.associado;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/associados")
@RequiredArgsConstructor
public class AssociadoController {

    private static final Logger logger = LoggerFactory.getLogger(AssociadoController.class);
    private final AssociadoService associadoService;

    @PostMapping
    public ResponseEntity<AssociadoResponseDTO> criar(@Valid @RequestBody AssociadoCreateDTO dto) {
        logger.info("Criando novo associado com CPF: {}", dto.cpf());

        AssociadoResponseDTO response = associadoService.criar(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        logger.info("Associado criado com sucesso - ID: {}", response.id());
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssociadoResponseDTO> buscarPorId(@PathVariable Long id) {
        logger.debug("Buscando associado por ID: {}", id);

        AssociadoResponseDTO response = associadoService.buscarPorId(id);

        logger.debug("Associado encontrado - ID: {}", id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<AssociadoResponseDTO> buscarPorCpf(@PathVariable String cpf) {
        logger.debug("Buscando associado por CPF: {}", cpf);

        AssociadoResponseDTO response = associadoService.buscarPorCpf(cpf);

        logger.debug("Associado encontrado - CPF: {}", cpf);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AssociadoResponseDTO>> listar() {
        logger.debug("Listando todos os associados");

        List<AssociadoResponseDTO> response = associadoService.listar();

        logger.debug("Total de associados retornados: {}", response.size());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssociadoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AssociadoPutDTO dto) {

        logger.info("Atualizando associado ID: {}", id);

        AssociadoResponseDTO response = associadoService.atualizar(id, dto);

        logger.info("Associado ID: {} atualizado com sucesso", id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AssociadoResponseDTO> atualizarParcial(
            @PathVariable Long id,
            @Valid @RequestBody AssociadoPatchDTO dto) {

        logger.info("Atualizando parcialmente associado ID: {}", id);

        AssociadoResponseDTO response = associadoService.atualizarParcial(id, dto);

        logger.info("Associado ID: {} atualizado parcialmente com sucesso", id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        logger.info("Excluindo associado ID: {}", id);

        associadoService.deletar(id);

        logger.info("Associado ID: {} excluído com sucesso", id);
        return ResponseEntity.noContent().build();
    }
}
