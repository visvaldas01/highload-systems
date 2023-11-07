package ru.ifmo.highloadsystems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.highloadsystems.model.entity.Tag;
import ru.ifmo.highloadsystems.repository.TagRepository;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagRepository tagRepository;

    @Autowired
    public TagController(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @GetMapping("/all")
    public List<Tag> getAll() {
        return tagRepository.findAll();
    }
}
