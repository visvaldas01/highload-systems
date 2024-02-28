package ru.ifmo.fileservice.model.dto;

import jakarta.validation.constraints.NotBlank;

public record UserDto(@NotBlank String username, @NotBlank String password) {
}