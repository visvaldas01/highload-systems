package ru.ifmo.highloadsystems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ifmo.highloadsystems.model.entity.Musician;

@Repository
public interface MusicianRepository extends JpaRepository<Musician, Long> {
}
