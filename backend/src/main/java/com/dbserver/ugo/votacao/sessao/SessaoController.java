package com.dbserver.ugo.votacao.sessao;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SessaoController {

    private final SessaoService sessaoService;

    @PostMapping("/pautas/{idPauta}/sessoes")
    public ResponseEntity<SessaoResponseDTO> criar(
            @PathVariable Long idPauta,
            @Valid @RequestBody SessaoCreateDTO dto) {

        SessaoResponseDTO response = sessaoService.criar(idPauta, dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/sessoes/{idSessao}")
    public ResponseEntity<SessaoResponseDTO> buscarPorId(@PathVariable Long idSessao) {
        return ResponseEntity.ok(sessaoService.buscarPorId(idSessao));
    }

    @GetMapping("/sessoes")
    public ResponseEntity<List<SessaoResponseDTO>> listar() {
        return ResponseEntity.ok(sessaoService.listar());
    }
}
