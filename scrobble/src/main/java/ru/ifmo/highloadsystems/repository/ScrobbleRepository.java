package ru.ifmo.highloadsystems.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.ifmo.highloadsystems.model.entity.Scrobble;

@Repository
public interface ScrobbleRepository extends ReactiveCrudRepository<Scrobble, Long> {
}
