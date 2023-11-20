package ru.ifmo.highloadsystems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.highloadsystems.model.dto.AlbumDto;
import ru.ifmo.highloadsystems.model.dto.MusicianDto;
import ru.ifmo.highloadsystems.model.dto.SongDto;
import ru.ifmo.highloadsystems.model.dto.TagDto;
import ru.ifmo.highloadsystems.model.entity.Album;
import ru.ifmo.highloadsystems.model.entity.Musician;
import ru.ifmo.highloadsystems.model.entity.Song;
import ru.ifmo.highloadsystems.model.entity.Tag;
import ru.ifmo.highloadsystems.repository.TagRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    private final TagRepository tagRepository;
    private final AlbumService albumService;
    private final SongService songService;
    private final MusicianService musicianService;


    @Autowired
    public TagService(TagRepository tagRepository, AlbumService albumService, SongService songService, MusicianService musicianService) {
        this.tagRepository = tagRepository;
        this.albumService = albumService;
        this.songService = songService;
        this.musicianService = musicianService;
    }

    public List<Tag> getAll() {
        return tagRepository.findAll();
    }

    public boolean addTag(TagDto tagDto) {
        Optional<Tag> optionalTag = tagRepository.findByName(tagDto.getName());
        if (optionalTag.isPresent()) {
            Tag modifiableTag = optionalTag.get();
            if (!tagDto.getMusicians().isEmpty()) {
                for (MusicianDto musician : tagDto.getMusicians()) {
                    Optional<Musician> musicianOptional = musicianService.findByName(musician.getName());
                    musicianOptional.ifPresent(value -> modifiableTag.getMusicians().add(value));
                }
                return true;
            } else if (!tagDto.getSongs().isEmpty()) {
                for (SongDto song : tagDto.getSongs()) {
                    Optional<Song> songOptional = songService.findByName(song.getName());
                    songOptional.ifPresent(value -> modifiableTag.getSongs().add(value));
                }
                return true;
            } else if (!tagDto.getAlbums().isEmpty()) {
                for (AlbumDto album : tagDto.getAlbums()) {
                    Optional<Album> albumOptional = albumService.findByName(album.getName());
                    albumOptional.ifPresent(value -> modifiableTag.getAlbums().add(value));
                }
                return true;
            } else
                return false;
        } else return false;
    }
}
