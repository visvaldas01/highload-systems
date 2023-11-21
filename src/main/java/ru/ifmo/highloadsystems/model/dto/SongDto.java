package ru.ifmo.highloadsystems.model.dto;

import lombok.Data;
import lombok.NonNull;

import java.util.Collection;

@Data
public class SongDto {
    @NonNull String name;
    Collection<MusicianDto> musician;
    Collection<AlbumDto> album;
}
