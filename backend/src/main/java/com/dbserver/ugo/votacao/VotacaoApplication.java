package com.dbserver.ugo.votacao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableAsync
@EnableScheduling
@SpringBootApplication
public class VotacaoApplication {
	public static void main(String[] args) {
		SpringApplication.run(VotacaoApplication.class, args);
        System.out.print("Bora");
	}
}
