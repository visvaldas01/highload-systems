package ru.ifmo.highloadsystems.rest;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Mono;
import ru.ifmo.highloadsystems.model.entity.Song;

import java.util.Optional;

@FeignClient(name = "song",
        configuration = FeignConfig.class)
public interface SongApi {
    @PostMapping(path = "/songs/find-by-name")
    Mono<Optional<Song>> findByName(String name);

    @PostMapping(path = "/songs/save")
    Mono<?> save(Song song);
}
