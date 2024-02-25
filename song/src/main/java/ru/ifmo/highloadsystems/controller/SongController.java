package ru.ifmo.highloadsystems.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.ifmo.highloadsystems.model.dto.SongDto;
import ru.ifmo.highloadsystems.model.entity.Song;
import ru.ifmo.highloadsystems.service.SongService;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/songs")
public class SongController {
    private final SongService songService;

    @GetMapping
    public Flux<Song> getAll(
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        return Flux.fromIterable(songService.getAll(PageRequest.of(offset, limit)));
    }

    @PostMapping
    public Mono<?> add(@Valid @RequestBody SongDto songDto) {
        songService.add(songDto);
        return Mono.empty();
    }

    //TODO
    @PostMapping("/add-to")
    public Mono<?> addTo(@Valid @RequestBody SongDto songDto) {
        songService.addTo(songDto);
        return Mono.empty();
    }

    @GetMapping("/recommendations")
    public Mono<Song> recommend(Principal principal) {
        return Mono.just(songService.recommend(principal.getName()));
    }

    @PostMapping(path = "/find-by-name")
    Mono<Optional<Song>> findByName(String name)
    {
        return Mono.just(songService.findByName(name));
    }

    @PostMapping(path = "/save")
    Mono<?> save(Song song)
    {
        songService.save(song);
        return Mono.empty();
    }
}
