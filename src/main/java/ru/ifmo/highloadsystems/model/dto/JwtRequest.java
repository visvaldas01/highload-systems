package ru.ifmo.highloadsystems.model.dto;

import jakarta.validation.constraints.NotNull;

public record JwtRequest(@NotNull String username, @NotNull String password) {
}
