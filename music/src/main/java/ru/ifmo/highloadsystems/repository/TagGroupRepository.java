package ru.ifmo.highloadsystems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ifmo.highloadsystems.model.entity.TagGroup;

import java.util.Optional;

@Repository
public interface TagGroupRepository extends JpaRepository<TagGroup, Long> {
    Optional<TagGroup> findByName(String name);
}
