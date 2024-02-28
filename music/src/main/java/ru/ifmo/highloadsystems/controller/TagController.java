package ru.ifmo.highloadsystems.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.highloadsystems.model.dto.TagDto;
import ru.ifmo.highloadsystems.model.entity.Tag;
import ru.ifmo.highloadsystems.service.TagService;

import java.util.Collection;
import java.util.List;

@io.swagger.v3.oas.annotations.tags.Tag(name = "Tag service controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    @Operation(summary = "Get list of all tags in the database")
    @GetMapping
    public ResponseEntity<List<Tag>> getAll() {
        return ResponseEntity.ok(tagService.getAll());
    }

    @Operation(summary = "Add a new tag to the database")
    @PostMapping
    public ResponseEntity<?> addTag(@Valid @RequestBody TagDto dto) {
        tagService.add(dto);
        return ResponseEntity.ok().build();
    }

    //TODO
    @Operation(summary = "Update tag info in the database")
    @PostMapping("/add_to_tag")
    public ResponseEntity<?> addToTag(@RequestHeader(value = "Authorization") String aut, @Valid @RequestBody TagDto tag) {
        tagService.addTag(aut, tag);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get list of tags from the database, given DTOs")
    @PostMapping(path = "/from-dto")
    ResponseEntity<Collection<Tag>> fromDto(Collection<TagDto> dto)
    {
        return ResponseEntity.ok(tagService.fromDto(dto));
    }
}
