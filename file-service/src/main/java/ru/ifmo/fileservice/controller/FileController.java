package ru.ifmo.fileservice.controller;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;

    @GetMapping
    public ResponseEntity<List<FileInfoResponse>> listUploadedFiles() {
        return ResponseEntity.ok(fileService.findAll());
    }

    @GetMapping("/{id}/info")
//    @PreAuthorize("@storageService.isOwnedByUser(authentication.name, #id)")
    public ResponseEntity<FileInfoResponse> getFileInfoById(@PathVariable @Positive @NotNull Long id, Principal principal) {
        try {
            FileInfoResponse fileInfoResponse = fileService.loadInfoById(id, principal.getName());
            return ResponseEntity.ok(fileInfoResponse);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{id}/data")
//    @PreAuthorize("@storageService.isOwnedByUser(authentication.name, #id)")
    public ResponseEntity<FileDataResponse> getFileDataById(@PathVariable @Positive @NotNull Long id, Principal principal) {
        try {
            FileDataResponse fileDataResponse = fileService.loadDataById(id, principal.getName());
            return ResponseEntity.ok(fileDataResponse);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/download/{filename:.+}")
    @ResponseBody
//    @PreAuthorize("@storageService.isOwnedByUser(authentication.name, #filename)")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename, Principal principal) {
        try {
            Resource file = fileService.loadAsResource(filename, principal.getName());
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + filename + "\"").body(file);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
//    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file, Principal principal) {
        try {
            fileService.store(file, principal.getName());
            return ResponseEntity.ok().build();
        } catch (StorageException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

}
