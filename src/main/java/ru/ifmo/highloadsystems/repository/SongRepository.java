package ru.ifmo.highloadsystems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ifmo.highloadsystems.model.entity.Song;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    Song findByName(String name);
}
