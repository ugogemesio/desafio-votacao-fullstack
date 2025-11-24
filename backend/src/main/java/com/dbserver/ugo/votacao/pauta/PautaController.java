package com.dbserver.ugo.votacao.pauta;

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
@RequestMapping("/api/v1/pautas")
@RequiredArgsConstructor
public class PautaController {

    private static final Logger logger = LoggerFactory.getLogger(PautaController.class);
    private final PautaService pautaService;

    @PostMapping
    public ResponseEntity<PautaResponseDTO> criar(@Valid @RequestBody PautaCreateDTO dto) {
        logger.info("Criando nova pauta: {}", dto.titulo());

        PautaResponseDTO response = pautaService.criar(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        logger.info("Pauta criada com sucesso - ID: {}, Título: {}", response.id(), response.titulo());
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PautaResponseDTO> buscarPorId(@PathVariable Long id) {
        logger.debug("Buscando pauta por ID: {}", id);

        PautaResponseDTO response = pautaService.buscarPorId(id);

        logger.debug("Pauta encontrada - ID: {}, Título: {}", response.id(), response.titulo());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PautaResponseDTO>> buscarPorStatus(@PathVariable PautaStatus status) {
        logger.debug("Buscando pautas por status :{}", status);

        List<PautaResponseDTO> response = pautaService.listarPorStatus(status);

        logger.debug("Encontradas {} pautas com status {}", response.size(), status);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PautaResponseDTO>> listar() {
        logger.debug("Listando todas as pautas");

        List<PautaResponseDTO> response = pautaService.listar();

        logger.debug("Retornando {} pautas", response.size());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PautaResponseDTO> atualizarParcial(
            @PathVariable Long id,
            @Valid @RequestBody PautaPatchDTO dto) {
        logger.info("Atualização parcial da pauta {}", id);

        PautaResponseDTO response = pautaService.atualizarParcial(id, dto);

        logger.info("Pauta ID: {} atualizada parcialmente com sucesso. Novo status: {}",
                id, response.status());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        logger.info("Solicitando exclusão da pauta {}", id);

        pautaService.deletar(id);

        logger.info("Pauta ID: {} excluída com sucesso", id);
        return ResponseEntity.noContent().build();
    }
}