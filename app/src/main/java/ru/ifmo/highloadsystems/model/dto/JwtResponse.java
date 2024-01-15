package ru.ifmo.user.model.dto;

import jakarta.validation.constraints.NotBlank;

public record JwtResponse(@NotBlank String token) {
}
