package ru.ifmo.highloadsystems.model.dto;

import lombok.Data;
import lombok.NonNull;
import ru.ifmo.highloadsystems.model.entity.Album;
import ru.ifmo.highloadsystems.model.entity.Musician;
import ru.ifmo.highloadsystems.model.entity.Song;
import ru.ifmo.highloadsystems.model.entity.Tag;

import java.util.Collection;
import java.util.Optional;

@Data
public class ScrobbleAnswerDto {
    Collection<Integer> nums;
    Collection<String> names;
}
