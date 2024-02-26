package ru.ifmo.user.controller;

import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.user.model.dto.RegistrationUserDto;
import ru.ifmo.user.model.dto.UserRoleDto;
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

    @PostMapping(path = "/find-by-username")
    ResponseEntity<Optional<User>> findByUsername(@RequestBody String username) {
        Optional<User> user = userService.findByUsername(username);
        return ResponseEntity.ok(user);
    }

    @PostMapping(path = "/load-user-bu-username")
    ResponseEntity<UserRoleDto> loadUserByUsername(@RequestBody String username) throws UsernameNotFoundException {
        return ResponseEntity.ok(userService.loadUserDtoByUsername(username));
    }

    @PostMapping(path = "/get-new-user")
    ResponseEntity<User> getNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        System.out.println("bbbbb");
        return ResponseEntity.ok(userService.getNewUser(registrationUserDto));
    }

    @GetMapping(path = "/get-all")
    ResponseEntity<List<User>> getAllInternal()
    {
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping(path = "/find-by-id")
    ResponseEntity<Optional<User>> findById(@RequestBody Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @DeleteMapping(path = "/delete-all")
    ResponseEntity<?> deleteAll() {
        userService.getAll();
        return ResponseEntity.ok().build();
    }
}
