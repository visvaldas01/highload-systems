package ru.ifmo.user.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Collection;

@Data
public class MusicianDto {
    @NotBlank String name;
    @Valid
    private Collection<SongDto> songs;
    @Valid
    private Collection<AlbumDto> albums;
    @Valid
    private Collection<TagDto> tags;
    @Valid
    private Collection<UserDto> users;
}
