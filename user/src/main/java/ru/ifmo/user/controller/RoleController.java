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
import ru.ifmo.user.model.entity.Role;
import ru.ifmo.user.model.entity.User;
import ru.ifmo.user.service.RoleService;
import ru.ifmo.user.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/get-user-role")
    public Role getUserRole()
    {
        return roleService.getUserRole();
    }
}
