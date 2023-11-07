package ru.ifmo.highloadsystems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.highloadsystems.model.entity.Scrobble;
import ru.ifmo.highloadsystems.repository.ScrobbleRepository;

import java.util.List;

@RestController
@RequestMapping("/scrobbles")
public class ScrobbleController {
    private final ScrobbleRepository scrobbleRepository;

    @Autowired
    public ScrobbleController(ScrobbleRepository scrobbleRepository) {
        this.scrobbleRepository = scrobbleRepository;
    }

    @GetMapping("/all")
    public List<Scrobble> getAll() {
        return scrobbleRepository.findAll();
    }
}
