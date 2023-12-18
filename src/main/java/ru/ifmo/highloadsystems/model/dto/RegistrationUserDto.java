package ru.ifmo.highloadsystems.model.dto;

import jakarta.validation.constraints.NotNull;

public record RegistrationUserDto(@NotNull String username, @NotNull String password, @NotNull String confirmPassword) {
}
