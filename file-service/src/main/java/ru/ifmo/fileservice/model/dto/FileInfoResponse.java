package ru.ifmo.fileservice.model.dto;

import lombok.Builder;

@Builder
public record FileInfoResponse(Long id, String name, String ownerName) {
}
