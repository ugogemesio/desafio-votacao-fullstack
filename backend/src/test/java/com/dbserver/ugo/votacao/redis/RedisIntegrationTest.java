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

        
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(3));

        
        String readValue = redisTemplate.opsForValue().get(key);
        assertEquals(value, readValue, "Valor inicial deve ser igual");

        
        Thread.sleep(4000);

        
        readValue = redisTemplate.opsForValue().get(key);
        assertNull(readValue, "Chave deve ter expirado");
    }
}
