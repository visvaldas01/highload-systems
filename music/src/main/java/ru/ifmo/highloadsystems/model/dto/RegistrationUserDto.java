package ru.ifmo.highloadsystems.model.dto;

import jakarta.validation.constraints.NotBlank;

public record RegistrationUserDto(@NotBlank String username, @NotBlank String password, @NotBlank String confirmPassword) {
}
