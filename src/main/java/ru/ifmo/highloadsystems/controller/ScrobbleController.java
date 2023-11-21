package ru.ifmo.highloadsystems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.highloadsystems.model.dto.ScrobbleDto;
import ru.ifmo.highloadsystems.model.entity.Scrobble;
import ru.ifmo.highloadsystems.service.ScrobbleService;

import java.util.List;

@RestController
@RequestMapping("/scrobbles")
public class ScrobbleController {
    private final ScrobbleService scrobbleService;

    @Autowired
    public ScrobbleController(ScrobbleService scrobbleService) {
        this.scrobbleService = scrobbleService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Scrobble>> getAll() {
        return ResponseEntity.ok(scrobbleService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<Scrobble> addScrobble(@RequestBody ScrobbleDto scrobbleDto) {
        return ResponseEntity.ok(scrobbleService.addScrobble(scrobbleDto));
    }
}