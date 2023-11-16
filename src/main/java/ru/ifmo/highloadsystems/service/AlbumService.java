package ru.ifmo.highloadsystems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.highloadsystems.model.dto.AlbumDto;
import ru.ifmo.highloadsystems.model.dto.SongDto;
import ru.ifmo.highloadsystems.model.entity.Album;
import ru.ifmo.highloadsystems.repository.AlbumRepository;
import ru.ifmo.highloadsystems.repository.SongRepository;

import java.util.List;

@Service
public class AlbumService {
    // TODO: только albumRepository
    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;

    @Autowired
    public AlbumService(AlbumRepository albumRepository, SongRepository songRepository) {
        this.albumRepository = albumRepository;
        this.songRepository = songRepository;
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
            update_album.getSongs().add(songRepository.findByName(song.getName()).orElseThrow());
        }
        return "Ok";
    }
}
