package ru.ifmo.highloadsystems.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TagGroupDto {
    @NotNull String name;
}