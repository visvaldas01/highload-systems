package ru.ifmo.highloadsystems.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.ifmo.highloadsystems.model.dto.ScrobbleAnswerDto;
import ru.ifmo.highloadsystems.model.dto.ScrobbleDto;
import ru.ifmo.highloadsystems.model.dto.ScrobbleRequestDto;
import ru.ifmo.highloadsystems.model.entity.Scrobble;
import ru.ifmo.highloadsystems.service.ScrobbleService;

import java.security.Principal;

@Tag(name = "Scrobble service controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/scrobbles")
public class ScrobbleController {
    private final ScrobbleService scrobbleService;

    @Operation(summary = "Get list of all scrobbles in the database")
    @GetMapping
    public Flux<Scrobble> getAll() {
        return scrobbleService.getAll();
    }

    @Operation(summary = "Add a new scrobble to the database")
    @PostMapping
    public Mono<Scrobble> addScrobble(@RequestHeader(value = "Authorization") String aut, @Valid @RequestBody ScrobbleDto scrobbleDto, Principal principal) {
        return scrobbleService.addScrobble(aut, scrobbleDto, principal.getName());
    }

    @Operation(summary = "Get song scrobbling statistics")
    @GetMapping("/stats")
    public Mono<ScrobbleAnswerDto> getStat(@Valid @RequestBody ScrobbleRequestDto scrobbleRequestDto, Principal principal) {
        return Mono.just(scrobbleService.getStatistic(scrobbleRequestDto, principal));
    }
}