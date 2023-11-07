package ru.ifmo.highloadsystems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.highloadsystems.model.entity.Album;
import ru.ifmo.highloadsystems.repository.AlbumRepository;

import java.util.List;

@RestController
@RequestMapping("/albums")
public class AlbumController {
    private final AlbumRepository albumRepository;

    @Autowired
    public AlbumController(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @GetMapping("/all")
    public List<Album> getAll() {
        return albumRepository.findAll();
    }
}
