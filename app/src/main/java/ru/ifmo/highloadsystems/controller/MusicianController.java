package ru.ifmo.highloadsystems.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.highloadsystems.model.dto.MusicianDto;
import ru.ifmo.highloadsystems.model.entity.Musician;
import ru.ifmo.highloadsystems.service.MusicianService;

import java.util.List;

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
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> add(@Valid @RequestBody MusicianDto musicianDto) {
        musicianService.add(musicianDto);
        return ResponseEntity.ok().build();
    }
}
