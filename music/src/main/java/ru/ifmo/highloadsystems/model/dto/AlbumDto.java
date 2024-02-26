package ru.ifmo.highloadsystems.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Collection;

@Data
public class AlbumDto {
    @NotBlank
    private String name;
    @Valid
    private Collection<SongDto> songs;
    @Valid
    private Collection<TagDto> tags;
    @Valid
    private Collection<MusicianDto> musicians;
}
