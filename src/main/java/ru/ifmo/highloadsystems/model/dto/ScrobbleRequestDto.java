package ru.ifmo.highloadsystems.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Optional;

@Data
public class ScrobbleRequestDto {
    Optional<String> username;
    @NotNull
    String requestTarget;
    @NotNull
    int size;
}
