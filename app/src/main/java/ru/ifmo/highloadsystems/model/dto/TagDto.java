package ru.ifmo.user.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Collection;

@Data
public class TagDto {
    @NotBlank String name;

    @Valid Collection<SongDto> songs;

    @Valid Collection<AlbumDto> albums;

    @Valid Collection<MusicianDto> musicians;

    @NotNull TagGroupDto tagGroup;
}
