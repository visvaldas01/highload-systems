package ru.ifmo.highloadsystems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.highloadsystems.exception.NothingToAddException;
import ru.ifmo.highloadsystems.model.dto.AlbumDto;
import ru.ifmo.highloadsystems.model.dto.MusicianDto;
import ru.ifmo.highloadsystems.model.dto.SongDto;
import ru.ifmo.highloadsystems.model.dto.TagDto;
import ru.ifmo.highloadsystems.model.entity.Album;
import ru.ifmo.highloadsystems.model.entity.Musician;
import ru.ifmo.highloadsystems.model.entity.Song;
import ru.ifmo.highloadsystems.model.entity.Tag;
import ru.ifmo.highloadsystems.repository.AlbumRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final SongService songService;
    private final TagService tagService;
    private final MusicianService musicianService;

    @Autowired
    public AlbumService(AlbumRepository albumRepository, SongService songService, TagService tagService, MusicianService musicianService) {
        this.albumRepository = albumRepository;
        this.songService = songService;
        this.tagService = tagService;
        this.musicianService = musicianService;
    }

    public List<Album> getAll()
    { return albumRepository.findAll(); }

    public String addNewAlbum(AlbumDto album)
    {
        Album newAlbum = new Album();
        newAlbum.setName(album.getName());
        albumRepository.save(newAlbum);
        return "Ok";
    }

    public String addSongsToAlbum(AlbumDto album) {
        Album update_album = albumRepository.findByName(album.getName()).orElseThrow();
        for (SongDto song : album.getSongs()) {
            update_album.getSongs().add(songService.findByName(song.getName()).orElseThrow());
        }
        return "Ok";
    }

    public Optional<Album> findByName(String name)
    { return albumRepository.findByName(name); }

    public void addToAlbum(AlbumDto albumDto) {
        Optional<Album> optionalAlbum = albumRepository.findByName(albumDto.getName());
        if (optionalAlbum.isPresent()) {
            Album modifiableAlbum = optionalAlbum.get();

            if (!albumDto.getSongs().isEmpty()) {
                for (SongDto song : albumDto.getSongs()) {
                    Optional<Song> songOptional = songService.findByName(song.getName());
                    if (songOptional.isPresent())
                        modifiableAlbum.getSongs().add(songOptional.get());
                    else {
                        songService.add(song);
                        modifiableAlbum.getSongs().add(songService.findByName(song.getName()).get());
                    }
                }
            } else if (!albumDto.getTags().isEmpty()) {
                for (TagDto tag : albumDto.getTags()) {
                    Optional<Tag> tagOptional = tagService.findByName(tag.getName());
                    if (tagOptional.isPresent())
                        modifiableAlbum.getTags().add(tagOptional.get());
                    else
                    {
                        tagService.add(tag);
                        modifiableAlbum.getTags().add(tagService.findByName(tag.getName()).get());
                    }
                }
            } else if (!albumDto.getMusicians().isEmpty()) {
                for (MusicianDto mus : albumDto.getMusicians()) {
                    Optional<Musician> musicianOptional = musicianService.findByName(mus.getName());
                    if (musicianOptional.isPresent())
                        modifiableAlbum.getMusicians().add(musicianOptional.get());
                    else
                    {
                        musicianService.add(mus);
                        modifiableAlbum.getMusicians().add(musicianService.findByName(mus.getName()).get());
                    }
                }
            }else
                throw new NothingToAddException("No data to add in message");
        } else throw new NothingToAddException("Tag not existing");
    }
}
