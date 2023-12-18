package ru.ifmo.highloadsystems.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Collection;

@Data
public class SongDto {
    @NotNull
    String name;
    Collection<MusicianDto> musician;
    Collection<AlbumDto> album;
    Collection<TagDto> tag;
}
