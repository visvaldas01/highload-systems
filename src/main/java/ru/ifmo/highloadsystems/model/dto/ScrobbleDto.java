package ru.ifmo.highloadsystems.model.dto;

import java.time.LocalDateTime;
import java.util.Date;

public record ScrobbleDto(Long songId, Long userId, LocalDateTime date) {
}
