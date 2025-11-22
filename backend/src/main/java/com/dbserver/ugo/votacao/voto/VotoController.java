package com.dbserver.ugo.votacao.voto;

import com.dbserver.ugo.votacao.exceptions.NegocioException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/votos")
@RequiredArgsConstructor
public class VotoController {

    private static final Logger logger = LoggerFactory.getLogger(VotoController.class);
    private final VotoService votoService;

    @PostMapping
    public ResponseEntity<?> votar(@Valid @RequestBody VotoCreateDTO dto) {

        logger.info("Registrando voto - Sessão: {}, CPF: {}, Valor: {}",
                dto.idSessao(), dto.cpfAssociado(), dto.valor());

        try {
            VotoResponseDTO response = votoService.votar(
                    dto.idSessao(),
                    dto.cpfAssociado(),
                    dto.valor()
            );

            logger.info("Voto registrado com sucesso - ID: {}", response.id());
            return ResponseEntity.ok(response);

        } catch (NegocioException e) {
            logger.warn("Falha ao registrar voto - Sessão: {}, CPF: {} - Motivo: {}",
                    dto.idSessao(), dto.cpfAssociado(), e.getMessage());

            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<VotoResponseDTO> buscarPorId(@PathVariable Long id) {

        logger.debug("Buscando voto por ID: {}", id);

        VotoResponseDTO response = votoService.buscar(id);

        logger.debug("Voto encontrado - ID: {}", id);
        return ResponseEntity.ok(response);
    }
}
