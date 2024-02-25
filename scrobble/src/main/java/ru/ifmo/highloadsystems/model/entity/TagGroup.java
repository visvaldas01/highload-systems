package ru.ifmo.highloadsystems.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
public class TagGroup {
    private Long id;

    private String name;

    @JsonIgnoreProperties("tag_groups")
    private Collection<Tag> tags;
}