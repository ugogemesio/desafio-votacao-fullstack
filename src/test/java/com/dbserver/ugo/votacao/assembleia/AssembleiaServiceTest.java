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
        assembleia.setNome("Assembleia Teste");
    }

    @Test
    void contextLoads() {
        assertThat(assembleiaService).isNotNull();
        assertThat(assembleiaRepository).isNotNull();
    }


}
