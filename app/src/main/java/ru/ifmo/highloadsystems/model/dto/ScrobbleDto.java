package ru.ifmo.highloadsystems.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScrobbleDto {
    @NotNull @Valid SongDto song;

    @NotNull LocalDateTime date;
}
