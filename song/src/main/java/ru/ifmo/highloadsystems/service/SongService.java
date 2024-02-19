package ru.ifmo.highloadsystems.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
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
import ru.ifmo.highloadsystems.rest.MusicianApi;
import ru.ifmo.highloadsystems.rest.UserApi;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;
    private final MusicianApi musicianApi;
    private final UserApi userApi;

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
                    Optional<Musician> musicianOptional = musicianApi.findByName(mus.getName()).getBody();
                    if (musicianOptional.isPresent()) modifiableSong.getMusicians().add(musicianOptional.get());
                    else {
                        musicianApi.add(mus);
                        modifiableSong.getMusicians().add(musicianApi.findByName(mus.getName()).getBody().orElseThrow());
                    }
                }
            } else throw new NothingToAddException("No data to add in song");
        } else throw new NothingToAddException("This song does not exist");
    }

    public Song recommend(String username) {
        Optional<User> user = userApi.findByUsername(username).getBody();
        if (user.isPresent()) {
            Recommendation rec = new Recommendation(user.get().getSongs(), songRepository);
            return switch (userApi.getUserRole().getBody().getName()) {
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
