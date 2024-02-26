package ru.ifmo.user.model.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

public record UserRoleDto(@NotBlank String username, @NotBlank String password, @NotBlank Collection<String> authorities) {
}
