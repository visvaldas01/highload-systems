package ru.ifmo.highloadsystems.rest;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.highloadsystems.model.dto.MusicianDto;
import ru.ifmo.highloadsystems.model.entity.Musician;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@FeignClient(name = "music",
        configuration = FeignConfig.class)
public interface MusicianApi {
    @GetMapping(path = "/musicians/find-by-name")
    ResponseEntity<Optional<Musician>> findByName(@RequestHeader(value = "Authorization")String authHeader, @RequestBody String name);

    @PostMapping(path = "/musicians")
    ResponseEntity<?> add(@RequestHeader(value = "Authorization")String authHeader, MusicianDto dto);

    @PostMapping(path = "/musicians/from-fto")
    ResponseEntity<Collection<Musician>> fromDto(@RequestHeader(value = "Authorization")String authHeader, Collection<MusicianDto> dto);

    @DeleteMapping(path = "/musicians/delete-all")
    ResponseEntity<?> deleteAll(@RequestHeader(value = "Authorization")String authHeader);
}
