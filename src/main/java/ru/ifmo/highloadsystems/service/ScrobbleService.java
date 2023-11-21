package ru.ifmo.highloadsystems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.stereotype.Service;
import ru.ifmo.highloadsystems.exception.NoPermissionException;
import ru.ifmo.highloadsystems.model.dto.ScrobbleDto;
import ru.ifmo.highloadsystems.model.entity.Scrobble;
import ru.ifmo.highloadsystems.model.entity.Song;
import ru.ifmo.highloadsystems.repository.ScrobbleRepository;

import java.util.List;

@Service
public class ScrobbleService {
    private final ScrobbleRepository scrobbleRepository;

    private final SongService songService;
    private final UserService userService;
    private final MusicianService musicianService;
    private final AuthService authService;


    @Autowired
    public ScrobbleService(ScrobbleRepository scrobbleRepository, SongService songService, UserService userService, MusicianService musicianService, AuthService authService) {
        this.scrobbleRepository = scrobbleRepository;
        this.songService = songService;
        this.userService = userService;
        this.musicianService = musicianService;
        this.authService = authService;
    }

    public List<Scrobble> getAll() {
        return scrobbleRepository.findAll();
    }

    public Scrobble save(Scrobble scrobble) {
        return scrobbleRepository.save(scrobble);
    }

    public Scrobble addScrobble(ScrobbleDto scrobbleDto)
    {
        if (authService.getUserFromContext().isEmpty())
            throw new NoPermissionException("Don't have rights to add scrobble");
        if (songService.findByName(scrobbleDto.song().getName()).isEmpty()) {
            Song song = Song.builder()
                    .musicians(musicianService.fromDto(scrobbleDto.song().getMusician()))
                    .name(scrobbleDto.song().getName())
                    .build();
            songService.save(song);
            return Scrobble.builder()
                    .song(song)
                    .user(userService.findById(authService.getUserFromContext().get().getId()).orElseThrow())
                    .date(scrobbleDto.date())
                    .build();
        } else {
            Scrobble scrobble = Scrobble.builder()
                    .song(songService.findByName(scrobbleDto.song().getName()).get())
                    .user(userService.findById(authService.getUserFromContext().get().getId()).orElseThrow())
                    .date(scrobbleDto.date())
                    .build();
            return save(scrobble);

        }

    }
}
