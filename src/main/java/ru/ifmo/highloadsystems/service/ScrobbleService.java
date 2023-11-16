package ru.ifmo.highloadsystems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.highloadsystems.model.entity.Scrobble;
import ru.ifmo.highloadsystems.repository.ScrobbleRepository;

import java.util.List;

@Service
public class ScrobbleService {
    private final ScrobbleRepository scrobbleRepository;

    @Autowired
    public ScrobbleService(ScrobbleRepository scrobbleRepository) {
        this.scrobbleRepository = scrobbleRepository;
    }

    public List<Scrobble> getAll() {
        return scrobbleRepository.findAll();
    }

    public Scrobble save(Scrobble scrobble) {
        return scrobbleRepository.save(scrobble);
    }
}
