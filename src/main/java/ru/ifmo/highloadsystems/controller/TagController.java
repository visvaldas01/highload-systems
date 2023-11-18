package ru.ifmo.highloadsystems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.highloadsystems.exception.AppError;
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

    @GetMapping("/all")
    public ResponseEntity<List<Tag>> getAll()
    { return ResponseEntity.ok(tagService.getAll()); }

    @GetMapping("/add")
    public ResponseEntity<?> addTags(@RequestBody TagDto tag)
    {
        if (tagService.addTag(tag))
            return ResponseEntity.ok().build();
        else
            return new ResponseEntity<>(new AppError(402, "Nothing to add"), HttpStatus.BAD_REQUEST);
    }
}
