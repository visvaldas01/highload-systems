package ru.ifmo.highloadsystems.rest;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import ru.ifmo.highloadsystems.model.dto.AlbumDto;
import ru.ifmo.highloadsystems.model.dto.MusicianDto;
import ru.ifmo.highloadsystems.model.dto.TagDto;
import ru.ifmo.highloadsystems.model.entity.Album;
import ru.ifmo.highloadsystems.model.entity.Musician;
import ru.ifmo.highloadsystems.model.entity.Tag;

import java.util.Collection;

@FeignClient(name = "music",
        configuration = FeignConfig.class)
public interface MusicApi {
    @PostMapping(path = "/musicians")
    ResponseEntity<?> add(MusicianDto dto);

    @PostMapping(path = "/musicians/from-fto")
    ResponseEntity<Collection<Musician>> fromDtoMusician(Collection<MusicianDto> dto);

    @PostMapping(path = "/tags/from-dto")
    ResponseEntity<Collection<Tag>> fromDtoTag(Collection<TagDto> dto);

    @PostMapping(path = "/albums/from-dto")
    ResponseEntity<Collection<Album>> fromDtoAlbum(Collection<AlbumDto> dto);
}
