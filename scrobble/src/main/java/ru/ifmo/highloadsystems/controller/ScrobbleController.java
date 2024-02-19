package ru.ifmo.highloadsystems.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.highloadsystems.model.dto.ScrobbleAnswerDto;
import ru.ifmo.highloadsystems.model.dto.ScrobbleDto;
import ru.ifmo.highloadsystems.model.dto.ScrobbleRequestDto;
import ru.ifmo.highloadsystems.model.entity.Scrobble;
import ru.ifmo.highloadsystems.service.ScrobbleService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/scrobbles")
public class ScrobbleController {
    private final ScrobbleService scrobbleService;

    @GetMapping
    public ResponseEntity<List<Scrobble>> getAll() {
        return ResponseEntity.ok(scrobbleService.getAll());
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Scrobble> addScrobble(@Valid @RequestBody ScrobbleDto scrobbleDto, Principal principal) {
        return ResponseEntity.ok(scrobbleService.addScrobble(scrobbleDto, principal.getName()));
    }

    @GetMapping("/stats")
    public ResponseEntity<ScrobbleAnswerDto> getStat(@Valid @RequestBody ScrobbleRequestDto scrobbleRequestDto, Principal principal) {
        return ResponseEntity.ok(scrobbleService.getStatistic(scrobbleRequestDto, principal));
    }
}