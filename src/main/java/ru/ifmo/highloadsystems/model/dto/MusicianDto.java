package ru.ifmo.highloadsystems.model.dto;

import lombok.Data;
import lombok.NonNull;

import java.util.Collection;

@Data
public class MusicianDto {
    @NonNull
    String name;

    private Collection<SongDto> songs;

    private Collection<AlbumDto> albums;

    private Collection<TagDto> tags;

    private Collection<UserDto> users;
}
