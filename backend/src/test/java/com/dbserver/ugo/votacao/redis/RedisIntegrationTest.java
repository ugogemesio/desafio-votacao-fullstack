package com.dbserver.ugo.votacao.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RedisIntegrationTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    void testRedisConnectionAndTTL() throws InterruptedException {
        String key = "integ:test:key";
        String value = "ok";

        // 1️⃣ Grava a chave com TTL de 3 segundos
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(3));

        // 2️⃣ Lê imediatamente
        String readValue = redisTemplate.opsForValue().get(key);
        assertEquals(value, readValue, "Valor inicial deve ser igual");

        // 3️⃣ Espera 4 segundos para expiração
        Thread.sleep(4000);

        // 4️⃣ Tenta ler de novo, deve ser null
        readValue = redisTemplate.opsForValue().get(key);
        assertNull(readValue, "Chave deve ter expirado");
    }
}
