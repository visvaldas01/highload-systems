package ru.ifmo.fileservice.model.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class ScrobbleAnswerDto {
    Collection<Integer> nums;
    Collection<String> names;
}
