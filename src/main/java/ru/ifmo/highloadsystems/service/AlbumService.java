package ru.ifmo.highloadsystems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.highloadsystems.model.dto.AlbumDto;
import ru.ifmo.highloadsystems.model.dto.SongDto;
import ru.ifmo.highloadsystems.model.entity.Album;
import ru.ifmo.highloadsystems.repository.AlbumRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final SongService songService;

    @Autowired
    public AlbumService(AlbumRepository albumRepository, SongService songService) {
        this.albumRepository = albumRepository;
        this.songService = songService;
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
            update_album.getSongs().add(songService.getByName(song.getName()).orElseThrow());
        }
        return "Ok";
    }

    public Optional<Album> getByName(String name)
    { return albumRepository.findByName(name); }
}
