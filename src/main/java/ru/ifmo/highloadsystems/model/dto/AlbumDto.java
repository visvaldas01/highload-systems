package ru.ifmo.highloadsystems.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

import java.util.Collection;

@Data
public class AlbumDto {
    @NotNull
    private String name;

    private Collection<SongDto> songs;
    private Collection<TagDto> tags;
    private Collection<MusicianDto> musicians;
}
