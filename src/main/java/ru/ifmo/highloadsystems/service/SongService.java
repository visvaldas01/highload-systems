package ru.ifmo.highloadsystems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.ifmo.highloadsystems.model.entity.Song;
import ru.ifmo.highloadsystems.repository.SongRepository;

import java.util.Optional;

@Service
public class SongService {
    private final SongRepository songRepository;

    @Autowired
    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public Page<Song> getAll(PageRequest of) {
        return songRepository.findAll(of);
    }

    public Optional<Song> findById(Long id) {
        return songRepository.findById(id);
    }

    public Optional<Song> findByName(String name) {
        return songRepository.findByName(name);
    }

    public Song save(Song song) {
        return songRepository.save(song);
    }
}
