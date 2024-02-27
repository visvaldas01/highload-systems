package ru.ifmo.highloadsystems.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.highloadsystems.model.dto.AlbumDto;
import ru.ifmo.highloadsystems.model.entity.Album;
import ru.ifmo.highloadsystems.service.AlbumService;

import java.util.Collection;
import java.util.List;

@Tag(name = "Album service controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/albums")
public class AlbumController {
    private final AlbumService albumService;


    @Operation(summary = "Get list of all albums in the database")
    @GetMapping
    public ResponseEntity<List<Album>> getAll() {
        return ResponseEntity.ok(albumService.getAll());
    }

    @Operation(summary = "Add a new album to the database")
    @PostMapping
    public ResponseEntity<?> addAlbum(@Valid @RequestBody AlbumDto album) {
        return ResponseEntity.ok(albumService.addNewAlbum(album));
    }

    @Operation(summary = "Update album info in the database")
    //TODO
    @PostMapping("/add_to_album")
    public ResponseEntity<String> addToAlbum(@Valid @RequestBody AlbumDto album) {
        albumService.addToAlbum(album);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get list of albums from the database, given DTOs")
    @PostMapping(path = "/from-dto")
    ResponseEntity<Collection<Album>> fromDto(Collection<AlbumDto> dto)
    {
        return ResponseEntity.ok(albumService.fromDto(dto));
    }
}
