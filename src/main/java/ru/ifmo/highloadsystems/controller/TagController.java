package ru.ifmo.highloadsystems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.highloadsystems.model.dto.TagDto;
import ru.ifmo.highloadsystems.model.entity.Tag;
import ru.ifmo.highloadsystems.service.TagService;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @Validated
    @GetMapping("/all")
    public ResponseEntity<List<Tag>> getAll() {
        return ResponseEntity.ok(tagService.getAll());
    }

    @Validated
    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addTag(@RequestBody TagDto dto) {
        tagService.add(dto);
        return ResponseEntity.ok().build();
    }

    @Validated
    @PostMapping("/add_to_tag")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addToTag(@RequestBody TagDto tag) {
        tagService.addTag(tag);
        return ResponseEntity.ok().build();
    }
}
