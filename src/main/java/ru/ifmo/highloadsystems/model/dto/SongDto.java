package ru.ifmo.highloadsystems.model.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class SongDto {
    @NonNull
    String name;
}
