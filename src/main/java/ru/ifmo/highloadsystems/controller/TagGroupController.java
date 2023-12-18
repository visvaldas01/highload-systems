package ru.ifmo.highloadsystems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.highloadsystems.model.dto.TagGroupDto;
import ru.ifmo.highloadsystems.model.entity.TagGroup;
import ru.ifmo.highloadsystems.service.TagGroupService;

import java.util.List;

@RestController
@RequestMapping("/tagGroups")
public class TagGroupController {
    private final TagGroupService tagGroupService;

    @Autowired
    public TagGroupController(TagGroupService tagGroupService) {
        this.tagGroupService = tagGroupService;
    }

    @Validated
    @GetMapping("/all")
    public ResponseEntity<List<TagGroup>> getAll() {
        return ResponseEntity.ok(tagGroupService.getAll());
    }

    @Validated
    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> add(@RequestBody TagGroupDto dto) {
        tagGroupService.add(dto);
        return ResponseEntity.ok().build();
    }
}
