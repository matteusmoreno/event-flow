package br.com.matteusmoreno.EventFlow.login.controller;

import br.com.matteusmoreno.EventFlow.login.request.LoginRequestDto;
import br.com.matteusmoreno.EventFlow.login.response.LoginResponseDto;
import br.com.matteusmoreno.EventFlow.login.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto request) {
        String jwtValue = loginService.login(request);

        return ResponseEntity.ok(new LoginResponseDto(jwtValue));
    }


}
