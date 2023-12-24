package ru.ifmo.highloadsystems.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.ifmo.highloadsystems.exception.AlreadyExistException;
import ru.ifmo.highloadsystems.exception.NoPermissionException;
import ru.ifmo.highloadsystems.exception.NothingToAddException;
import ru.ifmo.highloadsystems.model.dto.MusicianDto;
import ru.ifmo.highloadsystems.model.dto.SongDto;
import ru.ifmo.highloadsystems.model.entity.Musician;
import ru.ifmo.highloadsystems.model.entity.Song;
import ru.ifmo.highloadsystems.model.entity.User;
import ru.ifmo.highloadsystems.recommendation.Recommendation;
import ru.ifmo.highloadsystems.repository.SongRepository;

import java.util.*;

@Service
public class SongService {
    private final SongRepository songRepository;
    private final MusicianService musicianService;
    private final AuthService authService;
    private final RoleService roleService;

    @Autowired
    public SongService(SongRepository songRepository, MusicianService musicianService, AuthService authService, RoleService roleService) {
        this.songRepository = songRepository;
        this.musicianService = musicianService;
        this.authService = authService;
        this.roleService = roleService;
    }

    public Page<Song> getAll(PageRequest of) {
        return songRepository.findAll(of);
    }

    public Optional<Song> findByName(String name) {
        return songRepository.findByName(name);
    }

    public void save(Song song) {
        songRepository.save(song);
    }

    public void add(SongDto dto) {
        Optional<Song> optionalSong = findByName(dto.getName());
        if (optionalSong.isPresent()) throw new AlreadyExistException("This song already exists");
        else {
            Song song = new Song();
            song.setName(dto.getName());
            song.setVector1((float) 1.0);
            song.setVector2((float) 1.0);
            song.setVector3((float) 1.0);
            songRepository.save(song);
        }
    }

    @Transactional
    public void addTo(SongDto dto) {
        Optional<Song> optionalSong = songRepository.findByName(dto.getName());
        if (optionalSong.isPresent()) {
            Song modifiableSong = optionalSong.get();

            if (dto.getMusician() != null && !dto.getMusician().isEmpty()) {
                for (MusicianDto mus : dto.getMusician()) {
                    Optional<Musician> musicianOptional = musicianService.findByName(mus.getName());
                    if (musicianOptional.isPresent()) modifiableSong.getMusicians().add(musicianOptional.get());
                    else {
                        musicianService.add(mus);
                        modifiableSong.getMusicians().add(musicianService.findByName(mus.getName()).orElseThrow());
                    }
                }
            } else throw new NothingToAddException("No data to add in song");
        } else throw new NothingToAddException("This song does not exist");
    }

    public Song recommend() {
        Optional<User> user = authService.getUserFromContext();
        if (user.isPresent()) {
            Recommendation rec = new Recommendation(user.get().getSongs(), songRepository);
            return switch (roleService.getUserRole().getName()) {
                case "ROLE_USER" -> rec.GetNextSong(0);
                case "ROLE_ADMIN" -> rec.GetNextSong(-1);
                default -> throw new NoPermissionException("You don't have permission to get recommendation");
            };
        } else {
            List<Song> allSongs = songRepository.findAll();
            return allSongs.get((int) (Math.random() * allSongs.size()));
        }
    }

    public void deleteAll() {
        songRepository.deleteAll();
    }
}
