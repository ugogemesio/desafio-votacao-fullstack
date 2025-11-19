package com.dbserver.ugo.votacao.login.facade;

import com.dbserver.ugo.votacao.login.LoginResponseDTO;
import com.dbserver.ugo.votacao.login.LoginStatus;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CpfFacade {

    private final Random random = new Random();

    public LoginResponseDTO validar(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            return new LoginResponseDTO(cpf, LoginStatus.UNABLE_TO_VOTE);
        }
        LoginStatus status = random.nextBoolean() ? LoginStatus.ABLE_TO_VOTE : LoginStatus.UNABLE_TO_VOTE;
        return new LoginResponseDTO(cpf, status);
    }
}
