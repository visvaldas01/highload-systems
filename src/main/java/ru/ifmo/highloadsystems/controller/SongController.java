package ru.ifmo.highloadsystems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.highloadsystems.model.dto.SongDto;
import ru.ifmo.highloadsystems.model.entity.Song;
import ru.ifmo.highloadsystems.service.SongService;

@RestController
@RequestMapping("/songs")
public class SongController {
    private final SongService songService;

    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }

    @Validated
    @GetMapping("/all")
    public ResponseEntity<Page<Song>> getAll(
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit",defaultValue = "20") Integer limit)
    { return ResponseEntity.ok(songService.getAll(PageRequest.of(offset, limit))); }

    @Validated
    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> add(@RequestBody SongDto songDto) {
        songService.add(songDto);
        return ResponseEntity.ok().build();
    }

    @Validated
    @PostMapping("/add_to")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addTo(@RequestBody SongDto songDto) {
        songService.addTo(songDto);
        return ResponseEntity.ok().build();
    }

    @Validated
    @GetMapping("/recommend")
    public ResponseEntity<?> recommend() {
        return ResponseEntity.ok(songService.recommend());
    }
}
