package ru.ifmo.highloadsystems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ifmo.highloadsystems.model.entity.Album;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
}
