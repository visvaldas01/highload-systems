package ru.ifmo.highloadsystems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.highloadsystems.model.entity.Song;
import ru.ifmo.highloadsystems.repository.SongRepository;

import java.util.List;

@RestController
@RequestMapping("/songs")
public class SongController {
    private final SongRepository songRepository;

    @Autowired
    public SongController(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @GetMapping("/all")
    public List<Song> getAll() {
        return songRepository.findAll();
    }
}
