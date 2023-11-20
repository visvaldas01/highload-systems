package ru.ifmo.highloadsystems.model.dto;

import lombok.NonNull;

import java.time.LocalDateTime;

public record ScrobbleDto(@NonNull SongDto song, @NonNull LocalDateTime date) {
}
