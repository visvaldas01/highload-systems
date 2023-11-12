package ru.ifmo.highloadsystems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ifmo.highloadsystems.model.entity.Album;
import ru.ifmo.highloadsystems.model.entity.Song;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    Album findByName(String name);
}
