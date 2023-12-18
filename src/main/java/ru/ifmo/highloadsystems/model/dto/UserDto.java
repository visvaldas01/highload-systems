package ru.ifmo.highloadsystems.model.dto;

import jakarta.validation.constraints.NotNull;

public record UserDto(@NotNull String username, @NotNull String password) {
}
