package ru.ifmo.highloadsystems.model.dto;

import lombok.Data;
import lombok.NonNull;

import java.util.Collection;

@Data
public class TagDto {
    @NonNull
    String name;

    Collection<SongDto> songs;

    Collection<AlbumDto> albums;

    Collection<MusicianDto> musicians;

    TagGroupDto tagGroup;
}
