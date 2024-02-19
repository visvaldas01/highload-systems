package ru.ifmo.highloadsystems.rest;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.ifmo.highloadsystems.model.dto.SongDto;
import ru.ifmo.highloadsystems.model.entity.Song;

import java.util.Optional;

@FeignClient(name = "song",
        configuration = FeignConfig.class)
public interface SongApi {
    @PostMapping(path = "/songs/find-by-name")
    ResponseEntity<Optional<Song>> findByName(String name);

    @PostMapping(path = "/songs/add")
    void add(SongDto dto);
}
