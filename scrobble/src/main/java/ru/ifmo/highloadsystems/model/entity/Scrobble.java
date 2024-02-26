package ru.ifmo.highloadsystems.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "scrobbles")
public class Scrobble {
    @Id
    private Long id;
    private Long song;
    private Long user;
    private LocalDateTime date;

    public Scrobble(Long song_id, Long user_id, LocalDateTime date) {
        song = song_id;
        user = user_id;
        this.date = date;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Long getUser() {
        return user;
    }

    public Long getSong() {
        return song;
    }
}