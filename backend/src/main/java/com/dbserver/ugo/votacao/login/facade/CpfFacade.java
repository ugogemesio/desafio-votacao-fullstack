package com.dbserver.ugo.votacao.login.facade;

import com.dbserver.ugo.votacao.login.LoginResponseDTO;
import com.dbserver.ugo.votacao.login.Status;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CpfFacade {

    private final Random random = new Random();

    public LoginResponseDTO validar(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            return new LoginResponseDTO(cpf, Status.UNABLE_TO_VOTE);
        }
        Status status = random.nextBoolean() ? Status.ABLE_TO_VOTE : Status.UNABLE_TO_VOTE;
        return new LoginResponseDTO(cpf, status);
    }
}
