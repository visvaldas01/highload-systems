package ru.ifmo.highloadsystems.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Collection;

@Data
public class MusicianDto {
    @NotNull String name;

    private Collection<SongDto> songs;

    private Collection<AlbumDto> albums;

    private Collection<TagDto> tags;

    private Collection<UserDto> users;
}
