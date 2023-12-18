package ru.ifmo.highloadsystems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.highloadsystems.model.dto.ScrobbleAnswerDto;
import ru.ifmo.highloadsystems.model.dto.ScrobbleDto;
import ru.ifmo.highloadsystems.model.dto.ScrobbleRequestDto;
import ru.ifmo.highloadsystems.model.entity.Scrobble;
import ru.ifmo.highloadsystems.service.ScrobbleService;

import java.util.List;

@RestController
@Validated
@RequestMapping("/scrobbles")
public class ScrobbleController {
    private final ScrobbleService scrobbleService;

    @Autowired
    public ScrobbleController(ScrobbleService scrobbleService) {
        this.scrobbleService = scrobbleService;
    }

    @Validated
    @GetMapping("/all")
    public ResponseEntity<List<Scrobble>> getAll() {
        return ResponseEntity.ok(scrobbleService.getAll());
    }

    @Validated
    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Scrobble> addScrobble(@RequestBody ScrobbleDto scrobbleDto) {
        return ResponseEntity.ok(scrobbleService.addScrobble(scrobbleDto));
    }

    @Validated
    @GetMapping("/get_stat")
    public ResponseEntity<ScrobbleAnswerDto> getStat(@RequestBody ScrobbleRequestDto scrobbleRequestDto) {
        return ResponseEntity.ok(scrobbleService.getStatistic(scrobbleRequestDto));
    }
}