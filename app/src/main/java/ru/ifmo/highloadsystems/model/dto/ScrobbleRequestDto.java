package ru.ifmo.user.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ScrobbleRequestDto {
    String username;
    @NotBlank
    String requestTarget;
    @NotNull
    int size;
}
