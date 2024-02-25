package ru.ifmo.highloadsystems.model.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.Collection;

public record UserRoleDto(@NotBlank String username, @NotBlank String password, @NotBlank Collection<String> authorities) {
}
