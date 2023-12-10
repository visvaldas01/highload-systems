package ru.ifmo.highloadsystems.model.dto;

import lombok.Data;
import lombok.NonNull;

import java.util.Collection;
import java.util.Optional;

@Data
public class ScrobbleRequestDto {
    Optional<String> username;
    @NonNull
    String requestTarget;
    @NonNull
    int size;
}
