package ru.ifmo.highloadsystems.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.highloadsystems.model.entity.Song;
import ru.ifmo.highloadsystems.repository.SongRepository;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
public class SongsController {
    @Autowired
    private SongRepository songRepository;

    @GetMapping
    ResponseEntity<?> getSong(@Validated @RequestBody String name)
    {
        List<Song> song = songRepository.findByName(name);
        if (song.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(song.get(0).toString());
    }
}
