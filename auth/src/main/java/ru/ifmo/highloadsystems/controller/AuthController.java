package ru.ifmo.highloadsystems.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.highloadsystems.model.dto.JwtRequest;
import ru.ifmo.highloadsystems.model.dto.RegistrationUserDto;
import ru.ifmo.highloadsystems.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/token")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) throws AuthenticationException {
        return authService.createAuthToken(authRequest);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@Valid @RequestBody RegistrationUserDto registrationUserDto) {
        return authService.createNewUser(registrationUserDto);
    }
}
