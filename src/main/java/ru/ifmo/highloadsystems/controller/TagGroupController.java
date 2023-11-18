package ru.ifmo.highloadsystems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/all")
    public ResponseEntity<List<TagGroup>> getAll()
    { return ResponseEntity.ok(tagGroupService.getAll()); }
}
