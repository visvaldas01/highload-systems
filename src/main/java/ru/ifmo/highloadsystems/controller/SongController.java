package ru.ifmo.highloadsystems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/all")
    public ResponseEntity<Page<Song>> getAll(
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit",defaultValue = "20") Integer limit)
    { return ResponseEntity.ok(songService.getAll(PageRequest.of(offset, limit))); }
}
