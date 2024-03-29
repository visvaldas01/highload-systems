package ru.ifmo.highloadsystems.service;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.ifmo.highloadsystems.exception.RegisterException;
import ru.ifmo.highloadsystems.model.dto.JwtRequest;
import ru.ifmo.highloadsystems.model.dto.JwtResponse;
import ru.ifmo.highloadsystems.model.dto.RegistrationUserDto;
import ru.ifmo.highloadsystems.model.entity.User;
import ru.ifmo.highloadsystems.rest.UserApi;
import ru.ifmo.highloadsystems.utils.JwtTokensUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserApi userApi;
    private final JwtTokensUtils jwtTokensUtils;
    private final BCryptPasswordEncoder passwordEncoder;

    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) throws AuthenticationException {

        var userDetails = userApi.loadUserByUsername(authRequest.username()).getBody();

        if (userDetails == null)
            throw new AuthenticationException("Wrong username");

        if (!passwordEncoder.matches(authRequest.password(), userDetails.password()))
            throw new AuthenticationException("Wrong password exception");

        var token = jwtTokensUtils.generateToken(new org.springframework.security.core.userdetails.User(
                userDetails.username(),
                userDetails.password(),
                userDetails.authorities().stream().map(SimpleGrantedAuthority::new).toList()
        ));

        return ResponseEntity.ok(new JwtResponse(token));

    }

    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        if (!registrationUserDto.password().equals(registrationUserDto.confirmPassword())) {
            throw new RegisterException("Passwords not match");
        }
        System.out.println("1");
        ResponseEntity<Optional<User>> responseEntity = userApi.findByUsername(registrationUserDto.username());
        if (responseEntity.getBody().isPresent()) {
            throw new RegisterException("User with this name already exists");
        }
        System.out.println("2");
        var user = userApi.getNewUser(registrationUserDto).getBody();
        System.out.println("3");
        return ResponseEntity.ok(user.getUsername());
    }

    public Optional<User> getUserFromContext() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return userApi.findByUsername(name).getBody();
    }
}