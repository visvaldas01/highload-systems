package ru.ifmo.highloadsystems.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.ifmo.highloadsystems.exception.RegisterException;
import ru.ifmo.highloadsystems.model.dto.JwtRequest;
import ru.ifmo.highloadsystems.model.dto.JwtResponse;
import ru.ifmo.highloadsystems.model.dto.RegistrationUserDto;
import ru.ifmo.highloadsystems.model.entity.User;
import ru.ifmo.highloadsystems.rest.UserApi;
import ru.ifmo.highloadsystems.utils.JwtTokensUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserApi userApi;
    private final JwtTokensUtils jwtTokensUtils;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));

        var userDetails = userApi.loadUserByUsername(authRequest.username());
        var token = jwtTokensUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));

    }

    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        if (!registrationUserDto.password().equals(registrationUserDto.confirmPassword())) {
            throw new RegisterException("Passwords not match");
        }
        if (userApi.findByUsername(registrationUserDto.username()).isPresent()) {
            throw new RegisterException("User with this name already exists");
        }

        var user = userApi.getNewUser(registrationUserDto);
        return ResponseEntity.ok(user.getUsername());
    }

    public Optional<User> getUserFromContext() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return userApi.findByUsername(name);
    }
}