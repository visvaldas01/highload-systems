package ru.ifmo.fileservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.ifmo.fileservice.exception.StorageException;
import ru.ifmo.fileservice.model.dto.FileDataResponse;
import ru.ifmo.fileservice.model.dto.FileInfoResponse;
import ru.ifmo.fileservice.model.entity.File;
import ru.ifmo.fileservice.repository.FileRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    public List<FileInfoResponse> findAll() {
        Iterable<File> iterable = fileRepository.findAll();
        List<FileInfoResponse> all = new ArrayList<>();
        iterable.forEach(File -> all.add(new FileInfoResponse(File.getId(),
                File.getName(), File.getOwner())));
        return all;
    }

    public void store(MultipartFile file, String owner) throws StorageException {
        if (file.isEmpty()) {
            throw new StorageException("Не удалось сохранить пустой файл.");
        }
        try {
            String filename = file.getOriginalFilename();
            byte[] bytes = file.getBytes();
            File fileEntity = File.builder()
                    .name(filename)
                    .owner(owner)
                    .data(bytes).build();
            fileRepository.save(fileEntity);
        } catch (IOException e) {
            throw new StorageException("Не удалось сохранить файл.", e);
        }
    }

    public FileDataResponse loadDataByName(String filename, String owner) throws NoSuchElementException {
        File file = fileRepository.findByName(filename).orElseThrow();
        if (file.getOwner().equals(owner)) {
            byte[] content = file.getData();
            return new FileDataResponse(file.getId(), file.getName(), new String(content));
        }
        else throw new AccessDeniedException("You aren't the owner of this file");
    }

    public FileDataResponse loadDataById(Long fileId, String owner) throws NoSuchElementException {
        File file = fileRepository.findById(fileId).orElseThrow();
        if (file.getOwner().equals(owner)) {
            byte[] content = file.getData();
            return new FileDataResponse(file.getId(), file.getName(), new String(content));
        }
        else throw new AccessDeniedException("You aren't the owner of this file");
    }
    
    public FileInfoResponse loadInfoById(Long id, String owner) throws NoSuchElementException {
        File file = fileRepository.findById(id).orElseThrow();
        if (file.getOwner().equals(owner)) {
            return new FileInfoResponse(file.getId(), file.getName(), file.getOwner());
        }
        else throw new AccessDeniedException("You aren't the owner of this file");
    }

    public Resource loadAsResource(String filename, String owner) throws NoSuchElementException {
        return new ByteArrayResource(loadDataByName(filename, owner).data().getBytes());
    }
}