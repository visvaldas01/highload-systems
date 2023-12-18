package ru.ifmo.highloadsystems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ifmo.highloadsystems.exception.AlreadyExistException;
import ru.ifmo.highloadsystems.exception.NothingToAddException;
import ru.ifmo.highloadsystems.model.dto.MusicianDto;
import ru.ifmo.highloadsystems.model.dto.SongDto;
import ru.ifmo.highloadsystems.model.dto.TagDto;
import ru.ifmo.highloadsystems.model.entity.Musician;
import ru.ifmo.highloadsystems.model.entity.Song;
import ru.ifmo.highloadsystems.model.entity.Tag;
import ru.ifmo.highloadsystems.model.entity.TagGroup;
import ru.ifmo.highloadsystems.repository.TagRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    private final TagRepository tagRepository;
    private final SongService songService;
    private final MusicianService musicianService;
    private final TagGroupService tagGroupService;


    @Autowired
    public TagService(TagRepository tagRepository, SongService songService, MusicianService musicianService, TagGroupService tagGroupService) {
        this.tagRepository = tagRepository;
        this.songService = songService;
        this.musicianService = musicianService;
        this.tagGroupService = tagGroupService;
    }

    public List<Tag> getAll() {
        return tagRepository.findAll();
    }

    public void add(TagDto dto) {
        Optional<Tag> optionalTag = tagRepository.findByName(dto.getName());
        if (optionalTag.isPresent()) throw new AlreadyExistException("This tag already exists");
        else {
            Tag tag = new Tag();
            tag.setName(dto.getName());
            Optional<TagGroup> tagGroup = tagGroupService.findByName(dto.getTagGroup().getName());
            if (tagGroup.isPresent()) tag.setTagGroup(tagGroup.get());
            else {
                tagGroupService.add(dto.getTagGroup());
                tag.setTagGroup(tagGroupService.findByName(dto.getTagGroup().getName()).orElseThrow());
            }
            tagRepository.save(tag);
        }
    }

    @Transactional
    public void addTag(TagDto tagDto) {
        Optional<Tag> optionalTag = tagRepository.findByName(tagDto.getName());
        if (optionalTag.isPresent()) {
            Tag modifiableTag = optionalTag.get();
            if (tagDto.getMusicians() != null && !tagDto.getMusicians().isEmpty()) {
                for (MusicianDto musician : tagDto.getMusicians()) {
                    Optional<Musician> musicianOptional = musicianService.findByName(musician.getName());
                    if (musicianOptional.isPresent()) modifiableTag.getMusicians().add(musicianOptional.get());
                    else {
                        musicianService.add(musician);
                        modifiableTag.getMusicians().add(musicianService.findByName(musician.getName()).orElseThrow());
                    }
                }
            } else if (tagDto.getSongs() != null && !tagDto.getSongs().isEmpty()) {
                for (SongDto song : tagDto.getSongs()) {
                    Optional<Song> songOptional = songService.findByName(song.getName());
                    if (songOptional.isPresent()) modifiableTag.getSongs().add(songOptional.get());
                    else {
                        songService.add(song);
                        modifiableTag.getSongs().add(songService.findByName(song.getName()).orElseThrow());
                    }
                }
            } else throw new NothingToAddException("No data to add in message");
        } else throw new NothingToAddException("This tag does not exist");
    }

    public Collection<Tag> fromDto(Collection<TagDto> dto) {
        if (dto == null) return null;
        Collection<Tag> list = new ArrayList<>();
        for (TagDto tag : dto) {
            Optional<Tag> optionalTag = findByName(tag.getName());
            if (optionalTag.isPresent()) {
                list.add(optionalTag.get());
            } else {
                Tag tmpTag = new Tag();
                tmpTag.setName(tag.getName());
                Optional<TagGroup> optionalTagGroup = tagGroupService.findByName(tag.getTagGroup().getName());
                if (optionalTagGroup.isPresent())
                    tmpTag.setTagGroup(optionalTagGroup.get());
                else {
                    tagGroupService.add(tag.getTagGroup());
                    tmpTag.setTagGroup(tagGroupService.findByName(tag.getTagGroup().getName()).get());
                }
                list.add(tagRepository.save(tmpTag));
            }
        }
        return list;
    }

    public Optional<Tag> findByName(String name) {
        return tagRepository.findByName(name);
    }

    public void deleteAll() {
        tagRepository.deleteAll();
    }
}
