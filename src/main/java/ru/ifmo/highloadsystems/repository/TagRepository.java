package ru.ifmo.highloadsystems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ifmo.highloadsystems.model.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
