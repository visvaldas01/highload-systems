package ru.ifmo.fileservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.ifmo.fileservice.exception.StorageException;
import ru.ifmo.fileservice.model.dto.FileInfoResponse;
import ru.ifmo.fileservice.model.entity.File;
import ru.ifmo.fileservice.model.dto.FileDataResponse;
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
    
    public FileDataResponse loadDataByName(String filename) throws NoSuchElementException {
        File file = fileRepository.findByName(filename).orElseThrow();
        byte[] content = file.getData();
        return new FileDataResponse(file.getId(), file.getName(), new String(content));
    }
    
    public FileDataResponse loadDataById(Long fileId) throws NoSuchElementException {
        File file = fileRepository.findById(fileId).orElseThrow();
        byte[] content = file.getData();
        return new FileDataResponse(file.getId(), file.getName(), new String(content));
    }
    
    public FileInfoResponse loadInfoById(Long id) throws NoSuchElementException {
        File file = fileRepository.findById(id).orElseThrow();
        return new FileInfoResponse(file.getId(),
                file.getName(), file.getOwner());
    }
    
//    public FileInfoResponse loadInfoByName(String filename) throws NoSuchElementException {
//        File file = fileRepository.findByName(filename).orElseThrow();
//        return new FileInfoResponse(file.getId(),
//                file.getName(), file.getOwner());
//    }
//
//    public List<FileInfoResponse> listAll() {
//        Iterable<File> iterable = fileRepository.findAll();
//        List<FileInfoResponse> all = new ArrayList<>();
//        iterable.forEach(File -> {
//            all.add(new FileInfoResponse(File.getId(),
//                    File.getName(), File.getOwner()));
//        });
//        return all;
//    }
    
    public Resource loadAsResource(String filename) throws NoSuchElementException {
        return new ByteArrayResource(loadDataByName(filename).data().getBytes());
    }
    
//    public Resource loadAsResourceById(Long fileId) throws NoSuchElementException {
//        return new ByteArrayResource(loadDataById(fileId).data().getBytes());
//    }
//
//    public void delete(String filename) throws NoSuchElementException {
//        fileRepository.findByName(filename).orElseThrow();
//        fileRepository.deleteByName(filename);
//    }
//
//    public void deleteById(Long fileId) throws NoSuchElementException {
//        fileRepository.findById(fileId).orElseThrow();
//        fileRepository.deleteById(fileId);
//    }
//
//    public void deleteAll() {
//        fileRepository.deleteAll();
//    }
//
//    public boolean isOwnedByUser(String username, String filename) throws NoSuchElementException {
//        File file = fileRepository.findByName(filename).orElseThrow();
//        return file.getOwner().equals(username);
//    }
//
//    public boolean isOwnedByUser(String username, Long id) throws NoSuchElementException {
//        File file = fileRepository.findById(id).orElseThrow();
//        return file.getOwner().equals(username);
//    }

}