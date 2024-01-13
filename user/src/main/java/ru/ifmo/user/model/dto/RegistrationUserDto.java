package ru.ifmo.user.model.dto;

import jakarta.validation.constraints.NotBlank;

public record RegistrationUserDto(@NotBlank String username, @NotBlank String password, @NotBlank String confirmPassword) {
}
