package ru.ifmo.highloadsystems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.highloadsystems.exception.AlreadyExistException;
import ru.ifmo.highloadsystems.model.dto.TagDto;
import ru.ifmo.highloadsystems.model.dto.TagGroupDto;
import ru.ifmo.highloadsystems.model.entity.Tag;
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

    public void add(TagGroupDto dto)
    {
        Optional<TagGroup> optionalTagGroup = tagGroupRepository.findByName(dto.name());
        if (optionalTagGroup.isPresent())
            throw new AlreadyExistException("Song already exist");
        else
        {
            TagGroup tagGroup = new TagGroup();
            tagGroup.setName(dto.name());
            tagGroupRepository.save(tagGroup);
        }
    }

    public void deleteAll()
    { tagGroupRepository.deleteAll(); }
}
