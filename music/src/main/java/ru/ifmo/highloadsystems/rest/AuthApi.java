package ru.ifmo.highloadsystems.rest;


import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import jakarta.ws.rs.core.MediaType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.ifmo.highloadsystems.exception.RegisterException;
import ru.ifmo.highloadsystems.model.dto.JwtRequest;
import ru.ifmo.highloadsystems.model.dto.JwtResponse;
import ru.ifmo.highloadsystems.model.dto.RegistrationUserDto;
import ru.ifmo.highloadsystems.model.entity.User;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "user",
        configuration = FeignConfig.class)
public interface AuthApi {
    @GetMapping(path = "/create-auth-token", produces =  MediaType.APPLICATION_JSON)
    @Bulkhead(name = "a", fallbackMethod = "createAuthToken")
    ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest);

    @GetMapping(path = "/create-new-user", produces =  MediaType.APPLICATION_JSON)
    @Bulkhead(name = "b", fallbackMethod = "createNewUser")
    ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto);

    @GetMapping(path = "/get-user-from-context", produces =  MediaType.APPLICATION_JSON)
    @Bulkhead(name = "c", fallbackMethod = "getUserFromContext")
    public Optional<User> getUserFromContext();

    @Component
    class AithApiFallback implements AuthApi {
        @Override
        public ResponseEntity<?> createAuthToken(JwtRequest authRequest) {
            return null;
        }

        @Override
        public ResponseEntity<?> createNewUser(RegistrationUserDto registrationUserDto) {
            return null;
        }

        @Override
        public Optional<User> getUserFromContext() {
            return Optional.empty();
        }
    }
}
