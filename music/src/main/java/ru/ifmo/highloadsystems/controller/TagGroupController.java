package ru.ifmo.highloadsystems.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.highloadsystems.model.dto.TagGroupDto;
import ru.ifmo.highloadsystems.model.entity.TagGroup;
import ru.ifmo.highloadsystems.service.TagGroupService;

import java.util.List;

@Tag(name = "Tag group service controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/tagGroups")
public class TagGroupController {
    private final TagGroupService tagGroupService;

    @Operation(summary = "Get list of all tag groups in the database")
    @GetMapping
    public ResponseEntity<List<TagGroup>> getAll() {
        return ResponseEntity.ok(tagGroupService.getAll());
    }

    @Operation(summary = "Add a new tag group to the database")
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody TagGroupDto dto) {
        tagGroupService.add(dto);
        return ResponseEntity.ok().build();
    }
}
