package ru.ifmo.highloadsystems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.highloadsystems.model.entity.Musician;
import ru.ifmo.highloadsystems.service.MusicianService;

import java.util.List;

@RestController
@RequestMapping("/musicians")
public class MusicianController {
    private final MusicianService musicianService;
    @Autowired
    public MusicianController(MusicianService musicianService) {
        this.musicianService = musicianService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Musician>> getAll()
    { return ResponseEntity.ok(musicianService.getAll()); }
}
