package ru.ifmo.highloadsystems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.highloadsystems.model.entity.Musician;
import ru.ifmo.highloadsystems.repository.MusicianRepository;

import java.util.List;

@RestController
@RequestMapping("/musicians")
public class MusicianController {
    private final MusicianRepository musicianRepository;

    @Autowired
    public MusicianController(MusicianRepository musicianRepository) {
        this.musicianRepository = musicianRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Musician>> getAll() {
        return ResponseEntity.ok(musicianRepository.findAll());
    }
}
