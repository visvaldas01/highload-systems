package ru.ifmo.highloadsystems.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.highloadsystems.model.dto.TagDto;
import ru.ifmo.highloadsystems.model.entity.Tag;
import ru.ifmo.highloadsystems.service.TagService;

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
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addTag(@Valid @RequestBody TagDto dto) {
        tagService.add(dto);
        return ResponseEntity.ok().build();
    }

    //TODO
    @PostMapping("/add_to_tag")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addToTag(@Valid @RequestBody TagDto tag) {
        tagService.addTag(tag);
        return ResponseEntity.ok().build();
    }
}
