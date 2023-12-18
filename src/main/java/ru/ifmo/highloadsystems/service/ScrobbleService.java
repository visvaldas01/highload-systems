package ru.ifmo.highloadsystems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.highloadsystems.exception.NoPermissionException;
import ru.ifmo.highloadsystems.exception.NotImplemented;
import ru.ifmo.highloadsystems.model.dto.ScrobbleAnswerDto;
import ru.ifmo.highloadsystems.model.dto.ScrobbleDto;
import ru.ifmo.highloadsystems.model.dto.ScrobbleRequestDto;
import ru.ifmo.highloadsystems.model.entity.Scrobble;
import ru.ifmo.highloadsystems.model.entity.Song;
import ru.ifmo.highloadsystems.repository.ScrobbleRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Scrobble addScrobble(ScrobbleDto scrobbleDto) {
        if (authService.getUserFromContext().isEmpty())
            throw new NoPermissionException("Don't have rights to add scrobble");
        if (songService.findByName(scrobbleDto.getSong().getName()).isEmpty()) {
            Song song = Song.builder()
                    .musicians(musicianService.fromDto(scrobbleDto.getSong().getMusician()))
                    .name(scrobbleDto.getSong().getName())
                    .build();
            songService.save(song);
            return save(Scrobble.builder()
                    .song(song)
                    .user(userService.findById(authService.getUserFromContext().get().getId()).orElseThrow())
                    .date(scrobbleDto.getDate())
                    .build());
        } else {
            Scrobble scrobble = Scrobble.builder()
                    .song(songService.findByName(scrobbleDto.getSong().getName()).get())
                    .user(userService.findById(authService.getUserFromContext().get().getId()).orElseThrow())
                    .date(scrobbleDto.getDate())
                    .build();
            return save(scrobble);
        }
    }

    public ScrobbleAnswerDto getStatistic(ScrobbleRequestDto requestDto) {
        String username;
        if (requestDto.getUsername().isEmpty()) {
            if (authService.getUserFromContext().isEmpty())
                throw new NoPermissionException("You have to be authorized to get your stats");
            username = authService.getUserFromContext().get().getUsername();
        } else username = requestDto.getUsername().get();
        switch (requestDto.getRequestTarget()) {
            case "Song": {
                List<Scrobble> scrobbleList = scrobbleRepository.findAll();
                Map<String, Integer> listenMap = new HashMap<>();
                scrobbleList.stream().filter(scrobble -> scrobble.getUser().getUsername().equals(username)).
                        forEach(scrobble ->
                                listenMap.put(scrobble.getSong().getName(), listenMap.containsKey(scrobble.getSong().getName()) ?
                                        listenMap.get(scrobble.getSong().getName()) + 1 : 1));
                ScrobbleAnswerDto answerDto = new ScrobbleAnswerDto();
                answerDto.setNums(listenMap.values());
                answerDto.setNames(listenMap.keySet());
                return answerDto;
            }
            case "Musician": {
                List<Scrobble> scrobbleList = scrobbleRepository.findAll();
                Map<String, Integer> listenMap = new HashMap<>();
                scrobbleList.stream().filter(scrobble -> scrobble.getUser().getUsername().equals(username)).
                        forEach(scrobble -> scrobble.getSong().getMusicians().forEach(musician -> listenMap.put(musician.getName(),
                                listenMap.containsKey(musician.getName()) ? listenMap.get(musician.getName()) + 1 : 1)));
                ScrobbleAnswerDto answerDto = new ScrobbleAnswerDto();
                answerDto.setNums(listenMap.values());
                answerDto.setNames(listenMap.keySet());
                return answerDto;
            }
            case "Album": {
                List<Scrobble> scrobbleList = scrobbleRepository.findAll();
                Map<String, Integer> listenMap = new HashMap<>();
                scrobbleList.stream().filter(scrobble -> scrobble.getUser().getUsername().equals(username)).
                        forEach(scrobble -> scrobble.getSong().getAlbums().forEach(album -> listenMap.put(album.getName(),
                                listenMap.containsKey(album.getName()) ? listenMap.get(album.getName()) + 1 : 1)));
                ScrobbleAnswerDto answerDto = new ScrobbleAnswerDto();
                answerDto.setNums(listenMap.values());
                answerDto.setNames(listenMap.keySet());
                return answerDto;
            }
            case "Tag": {
                List<Scrobble> scrobbleList = scrobbleRepository.findAll();
                Map<String, Integer> listenMap = new HashMap<>();
                scrobbleList.stream().filter(scrobble -> scrobble.getUser().getUsername().equals(username)).
                        forEach(scrobble -> scrobble.getSong().getTags().forEach(tag -> listenMap.put(tag.getName(),
                                listenMap.containsKey(tag.getName()) ? listenMap.get(tag.getName()) + 1 : 1)));
                ScrobbleAnswerDto answerDto = new ScrobbleAnswerDto();
                answerDto.setNums(listenMap.values());
                answerDto.setNames(listenMap.keySet());
                return answerDto;
            }
            default:
                throw new NotImplemented();
        }
    }

    public void deleteAll() {
        scrobbleRepository.deleteAll();
    }
}
