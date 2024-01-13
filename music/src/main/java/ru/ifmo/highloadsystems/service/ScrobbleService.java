package ru.ifmo.highloadsystems.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ifmo.highloadsystems.exception.NoPermissionException;
import ru.ifmo.highloadsystems.exception.NotImplemented;
import ru.ifmo.highloadsystems.model.dto.ScrobbleAnswerDto;
import ru.ifmo.highloadsystems.model.dto.ScrobbleDto;
import ru.ifmo.highloadsystems.model.dto.ScrobbleRequestDto;
import ru.ifmo.highloadsystems.model.entity.Scrobble;
import ru.ifmo.highloadsystems.model.entity.Song;
import ru.ifmo.highloadsystems.repository.ScrobbleRepository;
import ru.ifmo.highloadsystems.rest.AuthApi;
import ru.ifmo.highloadsystems.rest.UserApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ScrobbleService {
    private final ScrobbleRepository scrobbleRepository;
    private final SongService songService;
    private final UserApi userApi;
    private final MusicianService musicianService;
    private final AuthApi authApi;
    private final AlbumService albumService;
    private final TagService tagService;

    public List<Scrobble> getAll() {
        return scrobbleRepository.findAll();
    }

    public Scrobble save(Scrobble scrobble) {
        return scrobbleRepository.save(scrobble);
    }

    @Transactional
    public Scrobble addScrobble(ScrobbleDto scrobbleDto) {
        if (authApi.getUserFromContext().isEmpty())
            throw new NoPermissionException("Don't have rights to add scrobble");
        if (songService.findByName(scrobbleDto.getSong().getName()).isEmpty()) {
            Song song = Song.builder()
                    .musicians(musicianService.fromDto(scrobbleDto.getSong().getMusician()))
                    .albums(albumService.fromDto(scrobbleDto.getSong().getAlbum()))
                    .tags(tagService.fromDto(scrobbleDto.getSong().getTag()))
                    .name(scrobbleDto.getSong().getName())
                    .build();
            songService.save(song);
            return save(Scrobble.builder()
                    .song(song)
                    .user(userApi.findById(authApi.getUserFromContext().get().getId()).orElseThrow())
                    .date(scrobbleDto.getDate())
                    .build());
        } else {
            Scrobble scrobble = Scrobble.builder()
                    .song(songService.findByName(scrobbleDto.getSong().getName()).get())
                    .user(userApi.findById(authApi.getUserFromContext().get().getId()).orElseThrow())
                    .date(scrobbleDto.getDate())
                    .build();
            return save(scrobble);
        }
    }

    public ScrobbleAnswerDto getStatistic(ScrobbleRequestDto requestDto) {
        String username;
        if (requestDto.getUsername() == null) {
            if (authApi.getUserFromContext().isEmpty())
                throw new NoPermissionException("You have to be authorized to get your stats");
            username = authApi.getUserFromContext().get().getUsername();
        } else username = requestDto.getUsername();
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
