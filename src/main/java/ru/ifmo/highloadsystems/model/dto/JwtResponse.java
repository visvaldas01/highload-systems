package ru.ifmo.highloadsystems.model.dto;

import jakarta.validation.constraints.NotNull;

public record JwtResponse(@NotNull String token) {
}
