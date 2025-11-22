package com.dbserver.ugo.votacao.sessao;

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
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SessaoController {

    private static final Logger logger = LoggerFactory.getLogger(SessaoController.class);
    private final SessaoService sessaoService;

    @PostMapping("/pautas/{idPauta}/sessoes")
    public ResponseEntity<SessaoResponseDTO> criar(
            @PathVariable Long idPauta,
            @Valid @RequestBody SessaoCreateDTO dto) {

        logger.info("Criando nova sessão para pauta {}. Duração: {} minutos",
                idPauta, dto.duracaoMinutos());

        SessaoResponseDTO response = sessaoService.criar(idPauta, dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        logger.info("Sessão criada com sucesso - ID: {}, Pauta ID: {}, Fechamento: {}",
                response.id(), idPauta, response.fechamento());
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/sessoes/{id}")
    public ResponseEntity<SessaoResponseDTO> buscarPorId(@PathVariable Long id) {
        logger.debug("Buscando sessão por ID {}", id);

        SessaoResponseDTO response = sessaoService.buscarPorId(id);

        logger.debug("Sessão encontrada - ID: {}, Status: {}", id, response.status());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/sessoes/{id}")
    public ResponseEntity<SessaoResponseDTO> encerraSessao(@PathVariable Long id) {
        logger.info("Solicitando encerramento da sessão {}", id);

        SessaoResponseDTO response = sessaoService.encerrarSessao(id);

        logger.info("Sessão ID: {} encerrada com sucesso. Status final: {}", id, response.status());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/sessoes")
    public ResponseEntity<List<SessaoResponseDTO>> listar() {
        logger.debug("Listando todas as sessões");

        List<SessaoResponseDTO> response = sessaoService.listar();

        logger.debug("Retornando {} sessões", response.size());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/sessoesPautas")
    public ResponseEntity<List<SessaoPautaDTO>> listarPorPautas() {
        logger.debug("Listando sessões com informações de pauta");

        List<SessaoPautaDTO> sessoes = sessaoService.listarComPauta();

        logger.debug("Retornando {} sessões com detalhes de pauta", sessoes.size());
        return ResponseEntity.ok(sessoes);
    }
}