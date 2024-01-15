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
import ru.ifmo.highloadsystems.model.entity.Role;
import ru.ifmo.highloadsystems.model.entity.User;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "role-service",
        url = "localhost:8082",
        configuration = FeignConfig.class)
public interface RoleApi {
    @GetMapping(path = "/get-user-role", produces =  MediaType.APPLICATION_JSON)
    @Bulkhead(name = "aa", fallbackMethod = "getUserRole")
    Role getUserRole();

    @Component
    class UserApiFallback implements RoleApi {

        @Override
        public Role getUserRole()
        {
            return null;
        }
    }
}