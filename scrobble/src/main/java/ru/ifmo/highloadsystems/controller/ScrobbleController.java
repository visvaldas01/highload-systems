package ru.ifmo.highloadsystems.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
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
    public Flux<Scrobble> getAll() {
        return scrobbleService.getAll();
    }

    @PostMapping
    public Mono<Scrobble> addScrobble(@Valid @RequestBody ScrobbleDto scrobbleDto, Principal principal) {
        return scrobbleService.addScrobble(scrobbleDto, principal.getName());
    }

    @GetMapping("/stats")
    public Mono<ScrobbleAnswerDto> getStat(@Valid @RequestBody ScrobbleRequestDto scrobbleRequestDto, Principal principal) {
        return Mono.just(scrobbleService.getStatistic(scrobbleRequestDto, principal));
    }
}