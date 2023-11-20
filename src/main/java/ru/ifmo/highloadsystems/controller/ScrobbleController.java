package ru.ifmo.highloadsystems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.highloadsystems.model.dto.ScrobbleDto;
import ru.ifmo.highloadsystems.model.entity.Scrobble;
import ru.ifmo.highloadsystems.model.entity.Song;
import ru.ifmo.highloadsystems.service.MusicianService;
import ru.ifmo.highloadsystems.service.ScrobbleService;
import ru.ifmo.highloadsystems.service.SongService;
import ru.ifmo.highloadsystems.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/scrobbles")
public class ScrobbleController {
    private final MusicianService musicianService;
    private final ScrobbleService scrobbleService;
    private final SongService songService;
    private final UserService userService;

    @Autowired
    public ScrobbleController(MusicianService musicianService, ScrobbleService scrobbleService, SongService songService, UserService userService) {
        this.musicianService = musicianService;
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
        if (songService.findByName(scrobbleDto.song().getName()).isEmpty()) {
            if (musicianService.findByName(scrobbleDto.song().getMusician()))
                Song song = Song.builder()
                        .name(scrobbleDto.song().getName())
                        .musicians(scrobbleDto.song().getMusician())
                        .build();
            songService.save()
        } else {
            Scrobble scrobble = Scrobble.builder()
                    .song(songService.findByName(scrobbleDto.song().getName()).get())
                    .user(userService.findById(scrobbleDto.username()).orElseThrow())
                    .date(scrobbleDto.date())
                    .build();

        }
        return ResponseEntity.ok(scrobbleService.save(scrobble));
    }
}