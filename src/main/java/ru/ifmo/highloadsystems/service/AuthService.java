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
import ru.ifmo.highloadsystems.model.dto.UserDto;
import ru.ifmo.highloadsystems.model.entity.User;
import ru.ifmo.highloadsystems.utils.JwtTokensUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtTokensUtils jwtTokensUtils;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));

        var userDetails = userService.loadUserByUsername(authRequest.username());
        var token = jwtTokensUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));

    }

    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        if (!registrationUserDto.password().equals(registrationUserDto.confirmPassword())) {
            throw new RegisterException("Passwords not match");
        }
        if (userService.findByUsername(registrationUserDto.username()).isPresent()) {
            throw new RegisterException("User with this name already exists");
        }

        var user = userService.getNewUser(registrationUserDto);
        return ResponseEntity.ok(new UserDto(user.getUsername(), user.getPassword()));
    }

    public Optional<User> getUserFromContext()
    {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByUsername(name);
    }
}