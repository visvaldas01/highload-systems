package ru.ifmo.highloadsystems.rest;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;
import ru.ifmo.highloadsystems.exception.AlreadyExistException;
import ru.ifmo.highloadsystems.exception.NoPermissionException;
import ru.ifmo.highloadsystems.exception.NothingToAddException;
import ru.ifmo.highloadsystems.model.dto.MusicianDto;
import ru.ifmo.highloadsystems.model.dto.RegistrationUserDto;
import ru.ifmo.highloadsystems.model.dto.SongDto;
import ru.ifmo.highloadsystems.model.entity.Musician;
import ru.ifmo.highloadsystems.model.entity.Role;
import ru.ifmo.highloadsystems.model.entity.Song;
import ru.ifmo.highloadsystems.model.entity.User;
import ru.ifmo.highloadsystems.recommendation.Recommendation;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "song",
        configuration = FeignConfig.class)
public interface SongApi {
    @PostMapping(path = "/songs/find-by-name")
    Mono<Optional<Song>> findByName(String name);

    @PostMapping(path = "/songs/save")
    Mono<?> save(Song song);
}
