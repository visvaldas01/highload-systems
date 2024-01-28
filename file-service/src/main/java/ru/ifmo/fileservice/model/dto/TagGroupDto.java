package ru.ifmo.fileservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TagGroupDto {
    @NotBlank String name;
}