package ru.ifmo.highloadsystems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.highloadsystems.model.dto.AlbumDto;
import ru.ifmo.highloadsystems.model.entity.Album;
import ru.ifmo.highloadsystems.service.AlbumService;

import java.util.List;

@RestController
@RequestMapping("/albums")
public class AlbumController {
    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Album>> getAll()
    { return ResponseEntity.ok(albumService.getAll()); }

    @PostMapping("/add")
    public ResponseEntity<?> addAlbum(@RequestBody AlbumDto album)
    { return ResponseEntity.ok(albumService.addNewAlbum(album)); }

    @PostMapping("/addSong")
    public ResponseEntity<String> addSongToAlbum(@RequestBody AlbumDto album)
    { return ResponseEntity.ok(albumService.addSongsToAlbum(album)); }
}
