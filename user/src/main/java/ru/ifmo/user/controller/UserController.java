package ru.ifmo.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.user.model.dto.RegistrationUserDto;
import ru.ifmo.user.model.dto.UserRoleDto;
import ru.ifmo.user.model.entity.User;
import ru.ifmo.user.service.UserService;

import java.util.List;
import java.util.Optional;

@Tag(name = "User service controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Get list of all registered users")
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    //TODO
    @Operation(summary = "Get user info from the database by his name")
    @PostMapping(path = "/find-by-username")
    ResponseEntity<Optional<User>> findByUsername(@RequestBody String username) {
        Optional<User> user = userService.findByUsername(username);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Get user info from the database by his name")
    @PostMapping(path = "/load-user-bu-username")
    ResponseEntity<UserRoleDto> loadUserByUsername(@RequestBody String username) throws UsernameNotFoundException {
        return ResponseEntity.ok(userService.loadUserDtoByUsername(username));
    }

    @Operation(summary = "Create new user using registration info")
    @PostMapping(path = "/get-new-user")
    ResponseEntity<User> getNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        return ResponseEntity.ok(userService.getNewUser(registrationUserDto));
    }

    @Operation(summary = "Get list of all registered users")
    @GetMapping(path = "/get-all")
    ResponseEntity<List<User>> getAllInternal()
    {
        return ResponseEntity.ok(userService.getAll());
    }

    @Operation(summary = "Get user info from the database by his id")
    @PostMapping(path = "/find-by-id")
    ResponseEntity<Optional<User>> findById(@RequestBody Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @Operation(summary = "Delete all registered users")
    @DeleteMapping(path = "/delete-all")
    ResponseEntity<?> deleteAll() {
        userService.getAll();
        return ResponseEntity.ok().build();
    }
}
