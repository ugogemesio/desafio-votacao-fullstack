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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class VotoRedisPerformanceTest {

    private static final Logger log = LoggerFactory.getLogger(VotoRedisPerformanceTest.class);

    @Autowired
    private VotoRepository votoRepository;

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
    private static final int TOTAL_ASSOCIADOS = 100_000;
    private static final int THREAD_POOL_SIZE = 8;
    private static final int BATCH_SIZE = 1000;

    @BeforeAll
    static void setupRedis() throws Exception {
        redisServer = RedisServer.builder()
                .port(6379)
                .setting("maxmemory 128M")
                .build();
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
        AtomicInteger votosProcessados = new AtomicInteger();
        AtomicInteger votosDuplicados = new AtomicInteger();
        List<Voto> batchVotos = new CopyOnWriteArrayList<>();

        long start = System.currentTimeMillis();
        log.info("Início do teste de stress com {} associados", TOTAL_ASSOCIADOS);

        for (int i = 0; i < associados.size(); i++) {
            final int index = i;
            executor.submit(() -> {
                try {
                    String cpf = associados.get(index).getCpf();
                    String chaveRedis = "sessao:" + sessao.getId() + ":cpf:" + cpf;

                    Boolean jaVotou = redisTemplate.opsForValue().setIfAbsent(chaveRedis, "true");
                    if (Boolean.FALSE.equals(jaVotou)) {
                        votosDuplicados.incrementAndGet();
                        return;
                    }
                    redisTemplate.expire(chaveRedis, Duration.ofMinutes(65));

                    Voto voto = new Voto();
                    voto.setAssociado(associados.get(index));
                    voto.setSessao(sessao);
                    voto.setOpcao(index % 2 == 0 ? VotoOpcao.SIM : VotoOpcao.NAO);

                    batchVotos.add(voto);
                    votosProcessados.incrementAndGet();

                    if (batchVotos.size() >= BATCH_SIZE) {
                        synchronized (batchVotos) {
                            votoRepository.saveAll(new ArrayList<>(batchVotos));
                            batchVotos.clear();
                        }
                    }
                } catch (Exception e) {
                    log.error("Erro processando voto para CPF {}: {}", associados.get(index).getCpf(), e.getMessage(), e);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        if (!batchVotos.isEmpty()) {
            votoRepository.saveAll(batchVotos);
        }

        long end = System.currentTimeMillis();
        long tempoTotal = end - start;
        long votosPorSegundo = (votosProcessados.get() * 1000L) / tempoTotal;

        log.info("=== RESULTADOS DO TESTE ===");
        log.info("Total de associados: {}", associados.size());
        log.info("Votos processados: {}", votosProcessados.get());
        log.info("Votos duplicados bloqueados: {}", votosDuplicados.get());
        log.info("Throughput: {} votos/s", votosPorSegundo);

        assertEquals(associados.size(), votosProcessados.get() + votosDuplicados.get());
    }
}
