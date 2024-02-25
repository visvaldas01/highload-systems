package ru.ifmo.fileservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ifmo.fileservice.model.entity.File;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    @Transactional
    Optional<File> findByName(String name);
}
