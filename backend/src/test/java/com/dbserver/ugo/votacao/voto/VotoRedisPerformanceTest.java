package com.dbserver.ugo.votacao.voto;

import com.dbserver.ugo.votacao.associado.Associado;
import com.dbserver.ugo.votacao.associado.AssociadoRepository;
import com.dbserver.ugo.votacao.pauta.Pauta;
import com.dbserver.ugo.votacao.pauta.PautaRepository;
import com.dbserver.ugo.votacao.pauta.PautaStatus;
import com.dbserver.ugo.votacao.sessao.Sessao;
import com.dbserver.ugo.votacao.sessao.SessaoRepository;
import com.dbserver.ugo.votacao.sessao.SessaoStatus;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import redis.embedded.RedisServer;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class VotoRedisPerformanceTest {

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private VotoMapper votoMapper;

    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private AssociadoRepository associadoRepository;

    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static RedisServer redisServer;
    private Sessao sessao;
    private List<Associado> associados;
    private static final int TOTAL_ASSOCIADOS = 100000;
    private static final int THREAD_POOL_SIZE = 8;

    @BeforeAll
    static void setupRedis() throws Exception {

        try {
            redisServer = new RedisServer(6379);
        } catch (Exception e) {
            redisServer = RedisServer.builder()
                    .port(6379)
                    .setting("maxmemory 128M")
                    .build();
        }
        redisServer.start();
    }

    @AfterAll
    static void tearDownRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }

    @BeforeEach
    void setup() {
        
        votoRepository.deleteAll();
        sessaoRepository.deleteAll();
        pautaRepository.deleteAll();
        associadoRepository.deleteAll();

        
        redisTemplate.getConnectionFactory().getConnection().flushDb();

        
        Pauta pauta = new Pauta();
        pauta.setTitulo("Pauta de Performance Test");
        pauta.setDescricao("Teste de carga com Redis");
        pauta.setStatus(PautaStatus.ABERTA);
        pauta = pautaRepository.save(pauta);

        
        sessao = new Sessao();
        sessao.setPauta(pauta);
        sessao.setAbertura(LocalDateTime.now());
        sessao.setDuracaoMinutos(60);
        sessao.setFechamento(sessao.getAbertura().plusMinutes(sessao.getDuracaoMinutos()));
        sessao.setStatus(SessaoStatus.ABERTA);
        sessao = sessaoRepository.save(sessao);

        
        associados = new ArrayList<>(TOTAL_ASSOCIADOS);
        for (int i = 0; i < TOTAL_ASSOCIADOS; i++) {
            Associado a = new Associado();
            a.setCpf(String.format("%011d", i));
            a.setNome("Associado " + i);
            associados.add(a);
        }
        associadoRepository.saveAll(associados);
    }

    @Test
    @Timeout(300)
    void stressTestVotosComRedis() throws InterruptedException {
        
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        CountDownLatch latch = new CountDownLatch(associados.size());
        AtomicInteger votosProcessados = new AtomicInteger(0);
        AtomicInteger votosDuplicados = new AtomicInteger(0);

        long start = System.currentTimeMillis();

        
        for (int i = 0; i < associados.size(); i++) {
            final int index = i;
            executor.submit(() -> {
                try {
                    String cpf = associados.get(index).getCpf();
                    String chaveRedis = "sessao:" + sessao.getId() + ":cpf:" + cpf;

                    // Verifica no Redis se já votou (operação atômica)
                    Boolean jaVotou = redisTemplate.opsForValue().setIfAbsent(chaveRedis, "true");

                    if (Boolean.FALSE.equals(jaVotou)) {
                        votosDuplicados.incrementAndGet();
                        return;
                    }

                    // Define expiração para a chave
                    redisTemplate.expire(chaveRedis, Duration.ofMinutes(65));

                    // CORREÇÃO: Criar o voto diretamente sem usar o mapper
                    Voto voto = new Voto();
                    voto.setAssociado(associados.get(index));
                    voto.setSessao(sessao);
                    voto.setOpcao(index % 2 == 0 ? VotoOpcao.SIM : VotoOpcao.NAO); // CORREÇÃO AQUI

                    votoRepository.save(voto);

                    votosProcessados.incrementAndGet();
                } catch (Exception e) {
                    System.err.println("Erro processando voto: " + e.getMessage());
                    e.printStackTrace(); // Adiciona stack trace completo
                } finally {
                    latch.countDown();
                }
            });
        }

        
        boolean completed = latch.await(2, TimeUnit.MINUTES);
        executor.shutdown();

        long end = System.currentTimeMillis();

        
        assertTrue(completed, "Teste não completou dentro do tempo limite");

        long tempoTotal = end - start;
        long votosPorSegundo = (votosProcessados.get() * 1000L) / tempoTotal;

        System.out.println("=== RESULTADOS DO TESTE ===");
        System.out.println("Total de associados: " + associados.size());
        System.out.println("Votos processados: " + votosProcessados.get());
        System.out.println("Votos duplicados bloqueados: " + votosDuplicados.get());
        System.out.println("Tempo total: " + tempoTotal + " ms");
        System.out.println("Votos por segundo: " + votosPorSegundo);

        assertEquals(associados.size(), votosProcessados.get() + votosDuplicados.get());
    }
}
