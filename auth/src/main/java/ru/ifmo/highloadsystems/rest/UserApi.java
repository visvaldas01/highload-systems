package ru.ifmo.highloadsystems.rest;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.ws.rs.core.MediaType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.ifmo.highloadsystems.model.dto.RegistrationUserDto;
import ru.ifmo.highloadsystems.model.dto.UserRoleDto;
import ru.ifmo.highloadsystems.model.entity.Role;
import ru.ifmo.highloadsystems.model.entity.User;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "user",
        configuration = FeignConfig.class)
public interface UserApi {
    @PostMapping(path = "/users/find-by-username")
    ResponseEntity<Optional<User>> findByUsername(@RequestBody String username);

    @PostMapping(path = "/users/load-user-bu-username")
    ResponseEntity<UserRoleDto> loadUserByUsername(@RequestBody String username) throws UsernameNotFoundException;

    @PostMapping(path = "/users/get-new-user")
    ResponseEntity<User> getNewUser(@RequestBody RegistrationUserDto registrationUserDto);

    @GetMapping(path = "/users/get-all")
    ResponseEntity<List<User>> getAll();

    @PostMapping(path = "/users/find-by-id")
    ResponseEntity<Optional<User>> findById(@RequestBody Long id);

    @DeleteMapping(path = "/users/delete-all")
    ResponseEntity<?> deleteAll();

    @GetMapping(path = "/role/get-user-role")
    ResponseEntity<Role> getUserRole();
}
