package ru.ifmo.highloadsystems.model.dto;

import lombok.Data;
import lombok.NonNull;

import java.util.Collection;

@Data
public class AlbumDto {
    @NonNull
    private String name;

    private Collection<SongDto> songs;
}
