package ru.ifmo.highloadsystems.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ScrobbleDto {
    @NotNull
    SongDto song;

    @NotNull
    LocalDateTime date;
}
