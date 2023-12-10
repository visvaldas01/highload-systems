package ru.ifmo.highloadsystems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ifmo.highloadsystems.model.entity.Scrobble;

import java.util.List;

@Repository
public interface ScrobbleRepository extends JpaRepository<Scrobble, Long> {
    List<Scrobble> findByUserUsername(String name);
 }
