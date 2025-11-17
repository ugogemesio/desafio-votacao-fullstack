package com.dbserver.ugo.votacao.login;

import com.dbserver.ugo.votacao.login.facade.CpfFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final CpfFacade cpfFacade;


    public Optional<LoginResponseDTO> login(String cpf) {
        LoginResponseDTO loginResponseDTO = cpfFacade.validar(cpf);
        if (loginResponseDTO.status() == Status.UNABLE_TO_VOTE) {
            return Optional.empty();
        }
        return Optional.of(loginResponseDTO);
    }
}
