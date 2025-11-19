package com.dbserver.ugo.votacao.voto;

import com.dbserver.ugo.votacao.exceptions.NegocioException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/votos")
@RequiredArgsConstructor
public class VotoController {

    private final VotoService votoService;

    @PostMapping
    public ResponseEntity<?> votar(@Valid @RequestBody VotoCreateDTO dto) {
        try {
            VotoResponseDTO response = votoService.votar(
                    dto.idSessao(),
                    dto.cpfAssociado(),
                    dto.valor()
            );
            return ResponseEntity.ok(response);

        } catch (NegocioException e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }



    @GetMapping("/{id}")
    public ResponseEntity<VotoResponseDTO> buscarPorId(@PathVariable Long id) {
        return votoService.buscar(id)
                .map(v -> ResponseEntity.ok(new VotoResponseDTO(
                        v.id(),
                        v.idAssociado(),
                        v.idSessao(),
                        v.opcao()
                )))
                .orElse(ResponseEntity.notFound().build());
    }
}
