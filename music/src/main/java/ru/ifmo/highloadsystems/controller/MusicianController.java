package ru.ifmo.highloadsystems.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.highloadsystems.model.dto.MusicianDto;
import ru.ifmo.highloadsystems.model.entity.Musician;
import ru.ifmo.highloadsystems.service.MusicianService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/musicians")
public class MusicianController {
    private final MusicianService musicianService;

    @GetMapping
    public ResponseEntity<List<Musician>> getAll() {
        return ResponseEntity.ok(musicianService.getAll());
    }

    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody MusicianDto musicianDto) {
        musicianService.add(musicianDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/from-fto")
    ResponseEntity<Collection<Musician>> fromDto(Collection<MusicianDto> dto)
    {
        return ResponseEntity.ok(musicianService.fromDto(dto));
    }

    @GetMapping(path = "/musicians/find-by-name")
    ResponseEntity<Optional<Musician>> findByName(String name)
    {
        return ResponseEntity.ok(musicianService.findByName(name));
    }
}
