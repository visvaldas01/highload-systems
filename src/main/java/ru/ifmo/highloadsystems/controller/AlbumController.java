package ru.ifmo.highloadsystems.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.highloadsystems.model.dto.AlbumDto;
import ru.ifmo.highloadsystems.model.entity.Album;
import ru.ifmo.highloadsystems.service.AlbumService;

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
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addAlbum(@Valid @RequestBody AlbumDto album) {
        return ResponseEntity.ok(albumService.addNewAlbum(album));
    }

    //TODO
    @PostMapping("/add_to_album")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> addToAlbum(@Valid @RequestBody AlbumDto album) {
        albumService.addToAlbum(album);
        return ResponseEntity.ok().build();
    }
}
