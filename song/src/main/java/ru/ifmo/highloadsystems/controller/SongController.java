package ru.ifmo.highloadsystems.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.ifmo.highloadsystems.model.dto.SongDto;
import ru.ifmo.highloadsystems.model.entity.Song;
import ru.ifmo.highloadsystems.service.SongService;

import java.security.Principal;
import java.util.Optional;

@Tag(name = "Song service controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/songs")
public class SongController {
    private final SongService songService;

    @Operation(summary = "Get list of all songs in the database")
    @GetMapping
    public Flux<Song> getAll(
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        return Flux.fromIterable(songService.getAll(PageRequest.of(offset, limit)));
    }

    @Operation(summary = "Add a new song to the database")
    @PostMapping
    public Mono<?> add(@Valid @RequestBody SongDto songDto) {
        songService.add(songDto);
        return Mono.empty();
    }

    //TODO
    @Operation(summary = "Update song info in the database")
    @PostMapping("/add-to")
    public Mono<?> addTo(@RequestHeader(value = "Authorization") String auth, @Valid @RequestBody SongDto songDto) {
        songService.addTo(auth, songDto);
        return Mono.empty();
    }

    @Operation(summary = "Get recommended song")
    @GetMapping("/recommendations")
    public Mono<Song> recommend(Principal principal) {
        return Mono.just(songService.recommend(principal.getName()));
    }

    @Operation(summary = "Get song info from the database by its name")
    @PostMapping(path = "/find-by-name")
    Mono<Optional<Song>> findByName(String name)
    {
        return Mono.just(songService.findByName(name));
    }

    @Operation(summary = "Save a song to the database")
    @PostMapping(path = "/save")
    Mono<?> save(Song song)
    {
        songService.save(song);
        return Mono.empty();
    }
}
