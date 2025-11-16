package com.dbserver.ugo.votacao.assembleia;

import com.dbserver.ugo.votacao.VotacaoApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = VotacaoApplication.class)
@AutoConfigureMockMvc
class AssembleiaServiceTest {

    @MockitoBean
    private AssembleiaRepository assembleiaRepository;

    @Autowired
    private AssembleiaService assembleiaService;

    @Autowired
    private AssembleiaMapper assembleiaMapper;
    private Assembleia assembleia;

    @BeforeEach
    void setUp() {
        assembleia = new Assembleia();
        assembleia.setNomeAssembleia("Assembleia Teste");
    }

    @Test
    void contextLoads() {
        assertThat(assembleiaService).isNotNull();
        assertThat(assembleiaRepository).isNotNull();
    }

    @Test
    void quandoBuscarPorNomeExistente_entaoDeveRetornarAssembleia() {

        String nome = "Teste";
        when(assembleiaRepository.findByNomeAssembleia(nome))
                .thenReturn(Arrays.asList(assembleia));

        List<AssembleiaResponseDTO> encontradas = assembleiaService.buscarPorNome(nome);

        assertThat(encontradas).isNotNull();
        assertThat(encontradas).hasSize(1);
        verify(assembleiaRepository).findByNomeAssembleia(nome);
    }

    @Test
    void quandoBuscarPorNomeInexistente_entaoDeveRetornarListaVazia() {
        // Given
        String nomeInexistente = "Inexistente";
        when(assembleiaRepository.findByNomeAssembleia(nomeInexistente))
                .thenReturn(Arrays.asList());


        List<AssembleiaResponseDTO> encontradas = assembleiaService.buscarPorNome(nomeInexistente);


        assertThat(encontradas).isNotNull();
        assertThat(encontradas).isEmpty();
        verify(assembleiaRepository).findByNomeAssembleia(nomeInexistente);
    }
//    @Test
//    void quandoCriarAssembleia_entaoRetornaResponseDTO() {
//
//        LocalDateTime agora = LocalDateTime.now();
//        AssembleiaCreateDTO createDTO = new AssembleiaCreateDTO("Nova Assembleia", agora, "null");
//
//        Assembleia entity = new Assembleia();
//        entity.setNomeAssembleia("Nova Assembleia");
//
//        Assembleia saved = new Assembleia();
//        saved.setIdAssembleia(1L);
//        saved.setNomeAssembleia("Nova Assembleia");
//
//        AssembleiaResponseDTO response = new AssembleiaResponseDTO(
//                1L, "Nova Assembleia", agora, "null"
//        );
//
//        when(assembleiaMapper.toEntity(createDTO)).thenReturn(entity);
//        when(assembleiaRepository.save(entity)).thenReturn(saved);
//        when(assembleiaMapper.toDTO(saved)).thenReturn(response);
//
//        AssembleiaResponseDTO resultado = assembleiaService.criar(createDTO);
//
//        assertThat(resultado).isNotNull();
//        assertThat(resultado.idAssembleia()).isEqualTo(1L);
//        assertThat(resultado.nomeAssembleia()).isEqualTo("Nova Assembleia");
//
//        verify(assembleiaMapper).toEntity(createDTO);
//        verify(assembleiaRepository).save(entity);
//        verify(assembleiaMapper).toDTO(saved);
//    }

//    @Test
//    void quandoAtualizarExistente_entaoRetornaDTOAtualizado() {
//        Long id = 1L;
//
//        AssembleiaUpdateDTO updateDTO = new AssembleiaUpdateDTO(
//                "Nome Atualizado",
//                "Descrição atualizada",
//                null
//        );
//
//        Assembleia entity = new Assembleia();
//        entity.setIdAssembleia(id);
//        entity.setNomeAssembleia("Antigo Nome");
//
//        Assembleia updated = new Assembleia();
//        updated.setIdAssembleia(id);
//        updated.setNomeAssembleia("Nome Atualizado");
//
//        AssembleiaResponseDTO response = new AssembleiaResponseDTO(
//                id,
//                "Nome Atualizado",
//                "Descrição atualizada",
//                null
//        );
//
//        when(assembleiaRepository.findById(id)).thenReturn(java.util.Optional.of(entity));
//        when(assembleiaRepository.save(entity)).thenReturn(updated);
//        when(assembleiaMapper.toDTO(updated)).thenReturn(response);
//
//        AssembleiaResponseDTO resultado = assembleiaService.atualizar(id, updateDTO);
//
//        assertThat(resultado.nomeAssembleia()).isEqualTo("Nome Atualizado");
//
//        verify(assembleiaRepository).findById(id);
//        verify(assembleiaMapper).updateEntityFromDTO(updateDTO, entity);
//        verify(assembleiaRepository).save(entity);
//        verify(assembleiaMapper).toDTO(updated);
//    }

}