package ru.ifmo.user.controller;

import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.user.model.dto.RegistrationUserDto;
import ru.ifmo.user.model.entity.User;
import ru.ifmo.user.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping(path = "/find-by-username", produces =  MediaType.APPLICATION_JSON)
    Optional<User> findByUsername(@RequestBody String username)
    {
        return userService.findByUsername(username);
    }

    @GetMapping(path = "/load-user-bu-username", produces =  MediaType.APPLICATION_JSON)
    UserDetails loadUserByUsername(@RequestBody String username) throws UsernameNotFoundException
    {
        return userService.loadUserByUsername(username);
    }

    @GetMapping(path = "/get-new-user", produces =  MediaType.APPLICATION_JSON)
    User getNewUser(@RequestBody RegistrationUserDto registrationUserDto)
    {
        return userService.getNewUser(registrationUserDto);
    }

    @GetMapping(path = "/get-all", produces =  MediaType.APPLICATION_JSON)
    List<User> getAllInternal()
    {
        return userService.getAll();
    }

    @GetMapping(path = "/find-by-id", produces =  MediaType.APPLICATION_JSON)
    Optional<User> findById(@RequestBody Long id)
    {
        return userService.findById(id);
    }

    @GetMapping(path = "/delete-all", produces =  MediaType.APPLICATION_JSON)
    void deleteAll()
    {
        userService.getAll();
    }
}
