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
import ru.ifmo.highloadsystems.model.entity.User;
import ru.ifmo.highloadsystems.repository.ScrobbleRepository;
import ru.ifmo.highloadsystems.rest.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScrobbleService {
    private final ScrobbleRepository scrobbleRepository;
    private final SongApi songApi;
    private final UserApi userApi;
    private final MusicApi musicApi;

    public List<Scrobble> getAll() {
        return scrobbleRepository.findAll();
    }

    public Scrobble save(Scrobble scrobble) {
        return scrobbleRepository.save(scrobble);
    }

    @Transactional
    public Scrobble addScrobble(ScrobbleDto scrobbleDto, String username) {
        Optional<User> user = userApi.findByUsername(username).getBody();
        if (!user.isPresent())
            throw new NoPermissionException("Don't have rights to add scrobble");
        if (songApi.findByName(scrobbleDto.getSong().getName()).block().isEmpty()) {
            Song song = Song.builder()
                    .musicians(musicApi.fromDtoMusician(scrobbleDto.getSong().getMusician()).getBody())
                    .albums(musicApi.fromDtoAlbum(scrobbleDto.getSong().getAlbum()).getBody())
                    .tags(musicApi.fromDtoTag(scrobbleDto.getSong().getTag()).getBody())
                    .name(scrobbleDto.getSong().getName())
                    .build();
            songApi.save(song);
            return save(Scrobble.builder()
                    .song(song)
                    .user(user.get())
                    .date(scrobbleDto.getDate())
                    .build());
        } else {
            Scrobble scrobble = Scrobble.builder()
                    .song(songApi.findByName(scrobbleDto.getSong().getName()).block().get())
                    .user(user.get())
                    .date(scrobbleDto.getDate())
                    .build();
            return save(scrobble);
        }
    }

    public ScrobbleAnswerDto getStatistic(ScrobbleRequestDto requestDto, Principal user) {
        String username;

        if (user != null)
            username = user.getName();
        else
            username = requestDto.getUsername();

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
