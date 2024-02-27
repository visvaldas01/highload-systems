package ru.ifmo.fileservice.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Collection;

@Data
public class SongDto {
    @NotBlank
    String name;
    @NotNull @Valid Collection<MusicianDto> musician;
    @Valid Collection<AlbumDto> album;
    @Valid Collection<TagDto> tag;
}
