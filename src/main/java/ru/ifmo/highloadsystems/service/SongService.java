package ru.ifmo.highloadsystems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.highloadsystems.model.entity.Song;
import ru.ifmo.highloadsystems.repository.SongRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SongService {
    private final SongRepository songRepository;

    @Autowired
    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public List<Song> getAll() {
        return songRepository.findAll();
    }

    public Optional<Song> getById(Long id) {
        return songRepository.findById(id);
    }

    public Optional<Song> getByName(String name) {
        return songRepository.findByName(name);
    }
}
