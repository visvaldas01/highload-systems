package ru.ifmo.highloadsystems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.highloadsystems.model.dto.ScrobbleDto;
import ru.ifmo.highloadsystems.model.entity.Scrobble;
import ru.ifmo.highloadsystems.service.ScrobbleService;
import ru.ifmo.highloadsystems.service.SongService;
import ru.ifmo.highloadsystems.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/scrobbles")
public class ScrobbleController {
    private final ScrobbleService scrobbleService;
    private final SongService songService;
    private final UserService userService;

    @Autowired
    public ScrobbleController(ScrobbleService scrobbleService, SongService songService, UserService userService) {
        this.scrobbleService = scrobbleService;
        this.songService = songService;
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Scrobble>> getAll() {
        return ResponseEntity.ok(scrobbleService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<Scrobble> addScrobble(@RequestBody ScrobbleDto scrobbleDto) {


        Scrobble scrobble = Scrobble.builder()
                .song(songService.getById(scrobbleDto.songId()).orElseThrow())
                .user(userService.getById(scrobbleDto.userId()).orElseThrow())
                .date(scrobbleDto.date())
                .build();


        return ResponseEntity.ok(scrobbleService.save(scrobble));
    }
}