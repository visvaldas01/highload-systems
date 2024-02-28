package ru.ifmo.fileservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.ifmo.fileservice.exception.StorageException;
import ru.ifmo.fileservice.model.dto.FileDataResponse;
import ru.ifmo.fileservice.model.dto.FileInfoResponse;
import ru.ifmo.fileservice.service.FileService;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;

@Tag(name = "File service controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;

    @Operation(summary = "Get list of all files in the database")
    @GetMapping
    public ResponseEntity<List<FileInfoResponse>> getAll() {
        return ResponseEntity.ok(fileService.findAll());
    }

    @Operation(summary = "Get file info by ID")
    @GetMapping("/{id}/info")
    public ResponseEntity<FileInfoResponse> getFileInfoById(@PathVariable @Positive @NotNull Long id, Principal principal) {
        try {
            FileInfoResponse fileInfoResponse = fileService.loadInfoById(id, principal.getName());
            return ResponseEntity.ok(fileInfoResponse);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Operation(summary = "Get file data by ID")
    @GetMapping("/{id}/data")
    public ResponseEntity<FileDataResponse> getFileDataById(@PathVariable @Positive @NotNull Long id, Principal principal) {
        try {
            FileDataResponse fileDataResponse = fileService.loadDataById(id, principal.getName());
            return ResponseEntity.ok(fileDataResponse);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Operation(summary = "Download file from the database by its name")
    @GetMapping("/download/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename, Principal principal) {
        try {
            Resource file = fileService.loadAsResource(filename, principal.getName());
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + filename + "\"").body(file);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Operation(summary = "Upload file to the database")
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file, Principal principal) {
        try {
            fileService.store(file, principal.getName());
            return ResponseEntity.ok().build();
        } catch (StorageException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

}
