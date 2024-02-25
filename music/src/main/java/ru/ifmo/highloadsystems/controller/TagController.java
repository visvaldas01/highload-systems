package ru.ifmo.highloadsystems.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.highloadsystems.model.dto.TagDto;
import ru.ifmo.highloadsystems.model.entity.Tag;
import ru.ifmo.highloadsystems.service.TagService;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<Tag>> getAll() {
        return ResponseEntity.ok(tagService.getAll());
    }

    @PostMapping
    public ResponseEntity<?> addTag(@Valid @RequestBody TagDto dto) {
        tagService.add(dto);
        return ResponseEntity.ok().build();
    }

    //TODO
    @PostMapping("/add_to_tag")
    public ResponseEntity<?> addToTag(@Valid @RequestBody TagDto tag) {
        tagService.addTag(tag);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/from-dto")
    ResponseEntity<Collection<Tag>> fromDto(Collection<TagDto> dto)
    {
        return ResponseEntity.ok(tagService.fromDto(dto));
    }
}
