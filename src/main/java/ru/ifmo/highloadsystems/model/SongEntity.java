package ru.ifmo.highloadsystems.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class SongEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
}