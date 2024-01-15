package ru.ifmo.highloadsystems.rest;


import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import jakarta.ws.rs.core.MediaType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.ifmo.highloadsystems.model.dto.RegistrationUserDto;
import ru.ifmo.highloadsystems.model.entity.User;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "jwt-service",
        url = "localhost:8080",
        configuration = FeignConfig.class)
public interface JwtApi {
    @GetMapping(path = "/generate-token", produces =  MediaType.APPLICATION_JSON)
    @Bulkhead(name = "a", fallbackMethod = "generateToken")
    String generateToken(UserDetails userDetails);

    @GetMapping(path = "/get-username", produces =  MediaType.APPLICATION_JSON)
    @Bulkhead(name = "b", fallbackMethod = "getUsername")
    String getUsername(String token);

    @GetMapping(path = "/get-roles", produces =  MediaType.APPLICATION_JSON)
    @Bulkhead(name = "c", fallbackMethod = "getRoles")
    List<String> getRoles(String token);

    @Component
    class UserApiFallback implements JwtApi {


        @Override
        public String generateToken(UserDetails userDetails) {
            return null;
        }

        @Override
        public String getUsername(String token) {
            return null;
        }

        @Override
        public List<String> getRoles(String token) {
            return null;
        }
    }
}
