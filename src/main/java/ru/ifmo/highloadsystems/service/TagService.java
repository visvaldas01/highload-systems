package ru.ifmo.highloadsystems.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.highloadsystems.exception.AlreadyExistException;
import ru.ifmo.highloadsystems.exception.NothingToAddException;
import ru.ifmo.highloadsystems.model.dto.AlbumDto;
import ru.ifmo.highloadsystems.model.dto.MusicianDto;
import ru.ifmo.highloadsystems.model.dto.SongDto;
import ru.ifmo.highloadsystems.model.dto.TagDto;
import ru.ifmo.highloadsystems.model.entity.*;
import ru.ifmo.highloadsystems.repository.TagRepository;

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

    public void add(TagDto dto)
    {
        Optional<Tag> optionalTag = tagRepository.findByName(dto.getName());
        if (optionalTag.isPresent())
            throw new AlreadyExistException("Tag already exist");
        else
        {
            Tag tag = new Tag();
            tag.setName(dto.getName());
            Optional<TagGroup> tagGroup = tagGroupService.findByName(dto.getTagGroup().name()) ;
            if (tagGroup.isPresent())
                tag.setTagGroup(tagGroup.get());
            else
            {
                tagGroupService.add(dto.getTagGroup());
                tag.setTagGroup(tagGroupService.findByName(dto.getTagGroup().name()).get());
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
                    if (musicianOptional.isPresent())
                        modifiableTag.getMusicians().add(musicianOptional.get());
                    else {
                        musicianService.add(musician);
                        modifiableTag.getMusicians().add(musicianService.findByName(musician.getName()).get());
                    }
                }
            } else if (tagDto.getSongs() != null && !tagDto.getSongs().isEmpty()) {
                for (SongDto song : tagDto.getSongs()) {
                    Optional<Song> songOptional = songService.findByName(song.getName());
                    if (songOptional.isPresent())
                        modifiableTag.getSongs().add(songOptional.get());
                    else {
                        songService.add(song);
                        modifiableTag.getSongs().add(songService.findByName(song.getName()).get());
                    }
                }
            } else
                throw new NothingToAddException("No data to add in message");
        } else throw new NothingToAddException("Tag not existing");
    }

    public Optional<Tag> findByName(String name)
    { return tagRepository.findByName(name); }

    public void deleteAll()
    { tagRepository.deleteAll(); }
}
