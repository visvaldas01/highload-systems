package ru.ifmo.highloadsystems.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.highloadsystems.model.dto.AlbumDto;
import ru.ifmo.highloadsystems.model.entity.Album;
import ru.ifmo.highloadsystems.service.AlbumService;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/albums")
public class AlbumController {
    private final AlbumService albumService;

    @GetMapping
    public ResponseEntity<List<Album>> getAll() {
        return ResponseEntity.ok(albumService.getAll());
    }

    @PostMapping
    public ResponseEntity<?> addAlbum(@Valid @RequestBody AlbumDto album) {
        return ResponseEntity.ok(albumService.addNewAlbum(album));
    }

    //TODO
    @PostMapping("/add_to_album")
    public ResponseEntity<String> addToAlbum(@Valid @RequestBody AlbumDto album) {
        albumService.addToAlbum(album);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/from-dto")
    ResponseEntity<Collection<Album>> fromDto(Collection<AlbumDto> dto)
    {
        return ResponseEntity.ok(albumService.fromDto(dto));
    }
}
