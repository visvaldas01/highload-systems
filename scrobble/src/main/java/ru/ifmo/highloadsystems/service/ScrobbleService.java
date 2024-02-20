package ru.ifmo.highloadsystems.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
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

    public Flux<Scrobble> getAll() {
        return scrobbleRepository.findAll();
    }

    public Mono<Scrobble> save(Scrobble scrobble) {
        return scrobbleRepository.save(scrobble);
    }

    @Transactional
    public Mono<Scrobble> addScrobble(ScrobbleDto scrobbleDto, String username) {
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
            return save(new Scrobble(
                    song.getId(),
                    user.get().getId(),
                    scrobbleDto.getDate()));
        } else {
            Scrobble scrobble = new Scrobble(
                    songApi.findByName(scrobbleDto.getSong().getName()).block().get().getId(),
                    user.get().getId(),
                    scrobbleDto.getDate());
            return save(scrobble);
        }
    }

    public ScrobbleAnswerDto getStatistic(ScrobbleRequestDto requestDto, Principal user) {
        String username;

        if (user != null)
            username = user.getName();
        else
            username = requestDto.getUsername();

        User requested_user = userApi.findByUsername(username).getBody().get();

        switch (requestDto.getRequestTarget()) {
            case "Song": {
                Flux<Scrobble> scrobbleList = scrobbleRepository.findAll();
                Map<Long, Integer> listenMap = new HashMap<>();
                scrobbleList.toStream().filter(scrobble -> scrobble.getUser() == requested_user.getId()).
                        forEach(scrobble ->
                                listenMap.put(scrobble.getSong(), listenMap.containsKey(scrobble.getSong()) ?
                                        listenMap.get(scrobble.getSong()) + 1 : 1));
                ScrobbleAnswerDto answerDto = new ScrobbleAnswerDto();
                answerDto.setNums(listenMap.values());
                answerDto.setNames(listenMap.keySet());
                return answerDto;
            }
//            case "Musician": {
//                Flux<Scrobble> scrobbleList = scrobbleRepository.findAll();
//                Map<String, Integer> listenMap = new HashMap<>();
//                scrobbleList.toStream().filter(scrobble -> scrobble.getUser()== requested_user.getId()).
//                        forEach(scrobble -> scrobble.getSong().getMusicians().forEach(musician -> listenMap.put(musician.getName(),
//                                listenMap.containsKey(musician.getName()) ? listenMap.get(musician.getName()) + 1 : 1)));
//                ScrobbleAnswerDto answerDto = new ScrobbleAnswerDto();
//                answerDto.setNums(listenMap.values());
//                answerDto.setNames(listenMap.keySet());
//                return answerDto;
//            }
//            case "Album": {
//                Flux<Scrobble> scrobbleList = scrobbleRepository.findAll();
//                Map<String, Integer> listenMap = new HashMap<>();
//                scrobbleList.toStream().filter(scrobble -> scrobble.getUser().getUsername().equals(username)).
//                        forEach(scrobble -> scrobble.getSong().getAlbums().forEach(album -> listenMap.put(album.getName(),
//                                listenMap.containsKey(album.getName()) ? listenMap.get(album.getName()) + 1 : 1)));
//                ScrobbleAnswerDto answerDto = new ScrobbleAnswerDto();
//                answerDto.setNums(listenMap.values());
//                answerDto.setNames(listenMap.keySet());
//                return answerDto;
//            }
//            case "Tag": {
//                Flux<Scrobble> scrobbleList = scrobbleRepository.findAll();
//                Map<String, Integer> listenMap = new HashMap<>();
//                scrobbleList.toStream().filter(scrobble -> scrobble.getUser().getUsername().equals(username)).
//                        forEach(scrobble -> scrobble.getSong().getTags().forEach(tag -> listenMap.put(tag.getName(),
//                                listenMap.containsKey(tag.getName()) ? listenMap.get(tag.getName()) + 1 : 1)));
//                ScrobbleAnswerDto answerDto = new ScrobbleAnswerDto();
//                answerDto.setNums(listenMap.values());
//                answerDto.setNames(listenMap.keySet());
//                return answerDto;
//            }
            default:
                throw new NotImplemented();
        }
    }

    public void deleteAll() {
        scrobbleRepository.deleteAll();
    }
}
