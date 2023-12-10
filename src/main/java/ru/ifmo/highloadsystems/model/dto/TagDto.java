package ru.ifmo.highloadsystems.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

import java.util.Collection;

@Data
public class TagDto {
    @NotNull
    String name;

    Collection<SongDto> songs;

    Collection<AlbumDto> albums;

    Collection<MusicianDto> musicians;

    TagGroupDto tagGroup;
}
