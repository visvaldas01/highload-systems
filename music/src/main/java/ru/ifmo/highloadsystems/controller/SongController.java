package ru.ifmo.highloadsystems.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.highloadsystems.model.dto.SongDto;
import ru.ifmo.highloadsystems.model.entity.Song;
import ru.ifmo.highloadsystems.service.SongService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/songs")
public class SongController {
    private final SongService songService;

    @GetMapping
    public ResponseEntity<Page<Song>> getAll(
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        return ResponseEntity.ok(songService.getAll(PageRequest.of(offset, limit)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> add(@Valid @RequestBody SongDto songDto) {
        songService.add(songDto);
        return ResponseEntity.ok().build();
    }

    //TODO
    @PostMapping("/add_to")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addTo(@Valid @RequestBody SongDto songDto) {
        songService.addTo(songDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/recommendations")
    public ResponseEntity<?> recommend(Principal principal) {
        return ResponseEntity.ok(songService.recommend(principal.getName()));
    }
}
