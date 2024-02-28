package ru.ifmo.highloadsystems.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Musician service controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/musicians")
public class MusicianController {
    private final MusicianService musicianService;

    @Operation(summary = "Get list of all albums in the database")
    @GetMapping
    public ResponseEntity<List<Musician>> getAll() {
        return ResponseEntity.ok(musicianService.getAll());
    }

    @Operation(summary = "Add a new musician to the database")
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody MusicianDto musicianDto) {
        musicianService.add(musicianDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get list of musicians from the database, given DTOs")
    @PostMapping(path = "/from-fto")
    ResponseEntity<Collection<Musician>> fromDto(Collection<MusicianDto> dto)
    {
        return ResponseEntity.ok(musicianService.fromDto(dto));
    }

    @Operation(summary = "Find musician info from the database by his name")
    @PostMapping(path = "/find-by-name")
    ResponseEntity<Optional<Musician>> findByName(@RequestBody String name)
    {
        return ResponseEntity.ok(musicianService.findByName(name));
    }
}
