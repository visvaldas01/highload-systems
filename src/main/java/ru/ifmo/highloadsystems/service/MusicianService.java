package ru.ifmo.highloadsystems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.highloadsystems.model.entity.Musician;
import ru.ifmo.highloadsystems.repository.MusicianRepository;

import java.util.List;

@Service
public class MusicianService {
    private final MusicianRepository musicianRepository;

    @Autowired
    public MusicianService(MusicianRepository musicianRepository) {
        this.musicianRepository = musicianRepository;
    }

    public List<Musician> getAll() {
        return musicianRepository.findAll();
    }
}