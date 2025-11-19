package com.dbserver.ugo.votacao.pauta;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pautas")
@RequiredArgsConstructor
public class PautaController {

    private final PautaService pautaService;

    @PostMapping
    public ResponseEntity<PautaResponseDTO> criar(@Valid @RequestBody PautaCreateDTO dto) {
        PautaResponseDTO response = pautaService.criar(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{idPauta}")
    public ResponseEntity<PautaResponseDTO> buscarPorId(@PathVariable Long idPauta) {
        PautaResponseDTO response = pautaService.buscarPorId(idPauta);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/status/{status}")
    public ResponseEntity<List<PautaResponseDTO>> buscarPorStatus(@PathVariable PautaStatus status) {
        List<PautaResponseDTO> response = pautaService.listarPorStatus(status);
        return ResponseEntity.ok(response);
    }



    @GetMapping
    public ResponseEntity<List<PautaResponseDTO>> listar() {
        List<PautaResponseDTO> response = pautaService.listar();
        return ResponseEntity.ok(response);
    }


    @PatchMapping("/{idPauta}")
    public ResponseEntity<PautaResponseDTO> atualizarParcial(
            @PathVariable Long idPauta,
            @Valid @RequestBody PautaPatchDTO dto) {
        PautaResponseDTO response = pautaService.atualizarParcial(idPauta, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{idPauta}")
    public ResponseEntity<Void> deletar(@PathVariable Long idPauta) {
        pautaService.deletar(idPauta);
        return ResponseEntity.noContent().build();
    }
}