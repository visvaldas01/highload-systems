package ru.ifmo.highloadsystems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ifmo.highloadsystems.model.entity.Song;

import java.util.List;

import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findByVector1BetweenAndVector2BetweenAndVector3Between(float min1, float max1, float min2, float max2, float min3, float max3);

    Optional<Song> findByName(String name);
}
