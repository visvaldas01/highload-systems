package ru.ifmo.highloadsystems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.highloadsystems.model.dto.ScrobbleAnswerDto;
import ru.ifmo.highloadsystems.model.dto.ScrobbleDto;
import ru.ifmo.highloadsystems.model.dto.ScrobbleRequestDto;
import ru.ifmo.highloadsystems.model.entity.Scrobble;
import ru.ifmo.highloadsystems.service.ScrobbleService;

import java.util.List;

@RestController
@Valid
@RequestMapping("/scrobbles")
public class ScrobbleController {
    private final ScrobbleService scrobbleService;

    @Autowired
    public ScrobbleController(ScrobbleService scrobbleService) {
        this.scrobbleService = scrobbleService;
    }

    @GetMapping
    public ResponseEntity<List<Scrobble>> getAll() {
        return ResponseEntity.ok(scrobbleService.getAll());
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Scrobble> addScrobble(@Valid @RequestBody ScrobbleDto scrobbleDto) {
        return ResponseEntity.ok(scrobbleService.addScrobble(scrobbleDto));
    }

    @GetMapping("/stats")
    public ResponseEntity<ScrobbleAnswerDto> getStat(@Valid @RequestBody ScrobbleRequestDto scrobbleRequestDto) {
        return ResponseEntity.ok(scrobbleService.getStatistic(scrobbleRequestDto));
    }
}