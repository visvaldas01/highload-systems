package ru.ifmo.highloadsystems.rest;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.ifmo.highloadsystems.model.dto.MusicianDto;
import ru.ifmo.highloadsystems.model.entity.Musician;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@FeignClient(name = "music",
        configuration = FeignConfig.class)
public interface MusicianApi {
    @GetMapping(path = "/musicians/find-by-name")
    ResponseEntity<Optional<Musician>> findByName(String name);

    @PostMapping(path = "/musicians")
    ResponseEntity<?> add(@RequestHeader(value = "Authorization")String authHeader, MusicianDto dto);

    @PostMapping(path = "/musicians/from-fto")
    ResponseEntity<Collection<Musician>> fromDto(Collection<MusicianDto> dto);

    @DeleteMapping(path = "/musicians/delete-all")
    ResponseEntity<?> deleteAll();
}
