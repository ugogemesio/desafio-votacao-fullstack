package com.dbserver.ugo.votacao.login;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {

        LoginResponseDTO loginResponse = loginService.login(request.cpf())
                .orElseGet(() -> new LoginResponseDTO(request.cpf(), Status.UNABLE_TO_VOTE));

        return ResponseEntity.ok(loginResponse);
    }

}