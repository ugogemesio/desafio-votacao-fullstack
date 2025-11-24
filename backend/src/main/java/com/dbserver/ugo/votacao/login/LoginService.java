package com.dbserver.ugo.votacao.login;

import com.dbserver.ugo.votacao.login.facade.CpfFacade;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
    private final CpfFacade cpfFacade;

    public Optional<LoginResponseDTO> login(String cpf) {
        logger.info("Tentativa de login com CPF: {}", cpf);
        LoginResponseDTO loginResponseDTO = cpfFacade.validar(cpf);

        if (loginResponseDTO.status() == LoginStatus.UNABLE_TO_VOTE) {
            logger.warn("CPF não autorizado para votação: {}", cpf);
            return Optional.empty();
        }

        logger.info("Login autorizado para CPF: {}", cpf);
        return Optional.of(loginResponseDTO);
    }
}