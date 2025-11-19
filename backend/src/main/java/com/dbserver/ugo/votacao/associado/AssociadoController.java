package com.dbserver.ugo.votacao.associado;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/associados")
@RequiredArgsConstructor
public class AssociadoController {

    private final AssociadoService associadoService;

    @PostMapping
    public ResponseEntity<AssociadoResponseDTO> criar(@Valid @RequestBody AssociadoCreateDTO dto) {
        AssociadoResponseDTO response = associadoService.criar(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssociadoResponseDTO> buscarPorId(@PathVariable Long id) {
        AssociadoResponseDTO response = associadoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<AssociadoResponseDTO> buscarPorCpf(@PathVariable String cpf) {
        AssociadoResponseDTO response = associadoService.buscarPorCpf(cpf);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AssociadoResponseDTO>> listar() {
        List<AssociadoResponseDTO> response = associadoService.listar();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssociadoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AssociadoPutDTO dto) {
        AssociadoResponseDTO response = associadoService.atualizar(id, dto);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AssociadoResponseDTO> atualizarParcial(
            @PathVariable Long id,
            @Valid @RequestBody AssociadoPatchDTO dto) {
        AssociadoResponseDTO response = associadoService.atualizarParcial(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        associadoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}