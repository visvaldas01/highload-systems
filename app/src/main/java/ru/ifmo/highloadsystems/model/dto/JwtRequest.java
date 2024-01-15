package ru.ifmo.user.model.dto;

import jakarta.validation.constraints.NotBlank;

public record JwtRequest(@NotBlank String username, @NotBlank String password) {
}
