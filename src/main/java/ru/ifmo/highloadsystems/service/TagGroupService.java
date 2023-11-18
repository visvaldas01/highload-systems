package ru.ifmo.highloadsystems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.highloadsystems.model.entity.TagGroup;
import ru.ifmo.highloadsystems.repository.TagGroupRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TagGroupService {
    private final TagGroupRepository tagGroupRepository;

    @Autowired
    public TagGroupService(TagGroupRepository tagGroupRepository) {
        this.tagGroupRepository = tagGroupRepository;
    }

    public List<TagGroup> getAll() {
        return tagGroupRepository.findAll();
    }

    public Optional<TagGroup> findByName(String name) { return tagGroupRepository.findByName(name); }
}
