package ru.ifmo.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.user.model.entity.Role;
import ru.ifmo.user.service.RoleService;

@Tag(name = "Role service controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {
    private final RoleService roleService;

    @Operation(summary = "Get user role")
    @GetMapping("/get-user-role")
    public Role getUserRole()
    {
        return roleService.getUserRole();
    }
}
