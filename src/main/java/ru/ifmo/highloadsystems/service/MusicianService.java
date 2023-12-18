package ru.ifmo.highloadsystems.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.highloadsystems.exception.AlreadyExistException;
import ru.ifmo.highloadsystems.model.dto.MusicianDto;
import ru.ifmo.highloadsystems.model.entity.Musician;
import ru.ifmo.highloadsystems.repository.MusicianRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

    public Optional<Musician> findByName(String name) {
        return musicianRepository.findByName(name);
    }

    public void add(MusicianDto dto) {
        Optional<Musician> optionalMusician = findByName(dto.getName());
        if (optionalMusician.isPresent()) throw new AlreadyExistException("This musician already exists");
        else {
            Musician musician = new Musician();
            musician.setName(dto.getName());
            musicianRepository.save(musician);
        }
    }

    public Collection<Musician> fromDto(Collection<MusicianDto> dto) {
        if (dto == null) return null;
        Collection<Musician> list = new ArrayList<>();
        for (MusicianDto musician : dto) {
            Optional<Musician> optionalMusician = findByName(musician.getName());
            if (optionalMusician.isPresent()) {
                list.add(optionalMusician.get());
            } else {
                Musician tmpMus = new Musician();
                tmpMus.setName(musician.getName());
                list.add(musicianRepository.save(tmpMus));
            }
        }
        return list;
    }

    public void deleteAll() {
        musicianRepository.deleteAll();
    }
}
