package ru.ifmo.highloadsystems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.ifmo.highloadsystems.exception.AlreadyExistException;
import ru.ifmo.highloadsystems.exception.NoPermissionException;
import ru.ifmo.highloadsystems.exception.NothingToAddException;
import ru.ifmo.highloadsystems.model.dto.AlbumDto;
import ru.ifmo.highloadsystems.model.dto.MusicianDto;
import ru.ifmo.highloadsystems.model.dto.SongDto;
import ru.ifmo.highloadsystems.model.entity.Album;
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
    private final AlbumService albumService;
    private final AuthService authService;
    private final RoleService roleService;

    @Autowired
    public SongService(SongRepository songRepository, MusicianService musicianService, AlbumService albumService, AuthService authService, RoleService roleService) {
        this.songRepository = songRepository;
        this.musicianService = musicianService;
        this.albumService = albumService;
        this.authService = authService;
        this.roleService = roleService;
    }

    public Page<Song> getAll(PageRequest of) {
        return songRepository.findAll(of);
    }

    public Optional<Song> findById(Long id) {
        return songRepository.findById(id);
    }

    public Optional<Song> findByName(String name) {
        return songRepository.findByName(name);
    }

    public Song save(Song song) {
        return songRepository.save(song);
    }

    public void add(SongDto dto) {
        Optional<Song> optionalSong = findByName(dto.getName());
        if (optionalSong.isPresent())
            throw new AlreadyExistException("Song already exist");
        else
        {
            Song song = new Song();
            song.setName(dto.getName());
            songRepository.save(song);
        }
    }

    public void addTo(SongDto dto) {
        Optional<Song> optionalSong = songRepository.findByName(dto.getName());
        if (optionalSong.isPresent()) {
            Song modifiableSong = optionalSong.get();

            if (!dto.getMusician().isEmpty()) {
                for (MusicianDto mus : dto.getMusician()) {
                    Optional<Musician> musicianOptional = musicianService.findByName(mus.getName());
                    if (musicianOptional.isPresent())
                        modifiableSong.getMusicians().add(musicianOptional.get());
                    else {
                        musicianService.add(mus);
                        modifiableSong.getMusicians().add(musicianService.findByName(mus.getName()).get());
                    }
                }
            } else if (!dto.getAlbum().isEmpty()) {
                for (AlbumDto alb : dto.getAlbum()) {
                    Optional<Album> albumOptional = albumService.findByName(alb.getName());
                    if (albumOptional.isPresent())
                        modifiableSong.getAlbums().add(albumOptional.get());
                    else
                    {
                        albumService.addNewAlbum(alb);
                        modifiableSong.getAlbums().add(albumService.findByName(alb.getName()).get());
                    }
                }
            }else
                throw new NothingToAddException("No data to add in song");
        } else throw new NothingToAddException("Song not existing");
    }

    public Collection<Song> fromDto(Collection<SongDto> dto)
    {
        Collection<Song> list = new ArrayList<>();
        for (SongDto mus: dto) {
            Optional<Song> optionalSong = findByName(mus.getName());
            if (optionalSong.isPresent())
            {
                list.add(optionalSong.get());
            }
            else
            {
                Song tmpMus = new Song();
                tmpMus.setName(mus.getName());
                list.add(tmpMus);
                songRepository.save(tmpMus);
            }
        }
        return list;
    }

    public Song recommend()
    {
        Optional<User> user = authService.getUserFromContext();
        if (user.isPresent())
        {
            Recommendation rec = new Recommendation(user.get().getSongs(), songRepository);
            switch (roleService.getUserRole().getName())
            {
                case "ROLE_USER": {
                    return rec.GetNextSong(0);
                }
                case "ROLE_ADMIN": {
                    return rec.GetNextSong(-1);
                }
                default:
                    throw new NoPermissionException("Don't have permission to have recommendation");
            }
        }
        else
        {
            List<Song> allSongs = songRepository.findAll();
            return allSongs.get((int) (Math.random() * allSongs.size()));
        }
    }
}
