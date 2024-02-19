package ru.ifmo.highloadsystems.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import ru.ifmo.highloadsystems.exception.AlreadyExistException;
import ru.ifmo.highloadsystems.exception.NothingToAddException;
import ru.ifmo.highloadsystems.model.dto.AlbumDto;
import ru.ifmo.highloadsystems.model.dto.MusicianDto;
import ru.ifmo.highloadsystems.model.dto.SongDto;
import ru.ifmo.highloadsystems.model.dto.TagDto;
import ru.ifmo.highloadsystems.model.entity.Album;
import ru.ifmo.highloadsystems.model.entity.Musician;
import ru.ifmo.highloadsystems.model.entity.Song;
import ru.ifmo.highloadsystems.model.entity.Tag;
import ru.ifmo.highloadsystems.repository.AlbumRepository;
import ru.ifmo.highloadsystems.rest.SongApi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final SongApi songApi;
    private final TagService tagService;
    private final MusicianService musicianService;

    public List<Album> getAll() {
        return albumRepository.findAll();
    }

    public String addNewAlbum(AlbumDto album) {
        Optional<Album> optionalAlbum = albumRepository.findByName(album.getName());
        if (optionalAlbum.isPresent()) throw new AlreadyExistException("This album already exists");
        else {
            Album newAlbum = new Album();
            newAlbum.setName(album.getName());
            albumRepository.save(newAlbum);
        }
        return "Ok";
    }

    public Optional<Album> findByName(String name) {
        return albumRepository.findByName(name);
    }

    @Transactional
    public void addToAlbum(AlbumDto albumDto) {
        Optional<Album> optionalAlbum = albumRepository.findByName(albumDto.getName());
        if (optionalAlbum.isPresent()) {
            Album modifiableAlbum = optionalAlbum.get();

            if (albumDto.getSongs() != null && !albumDto.getSongs().isEmpty()) {
                for (SongDto song : albumDto.getSongs()) {
                    Optional<Song> songOptional = songApi.findByName(song.getName()).block();
                    if (songOptional.isPresent()) modifiableAlbum.getSongs().add(songOptional.get());
                    else {
                        songApi.add(song);
                        modifiableAlbum.getSongs().add(songApi.findByName(song.getName()).block().get());
                    }
                }
            } else if (albumDto.getTags() != null && !albumDto.getTags().isEmpty()) {
                for (TagDto tag : albumDto.getTags()) {
                    Optional<Tag> tagOptional = tagService.findByName(tag.getName());
                    if (tagOptional.isPresent()) modifiableAlbum.getTags().add(tagOptional.get());
                    else {
                        tagService.add(tag);
                        modifiableAlbum.getTags().add(tagService.findByName(tag.getName()).orElseThrow());
                    }
                }
            } else if (albumDto.getMusicians() != null && !albumDto.getMusicians().isEmpty()) {
                for (MusicianDto mus : albumDto.getMusicians()) {
                    Optional<Musician> musicianOptional = musicianService.findByName(mus.getName());
                    if (musicianOptional.isPresent()) modifiableAlbum.getMusicians().add(musicianOptional.get());
                    else {
                        musicianService.add(mus);
                        modifiableAlbum.getMusicians().add(musicianService.findByName(mus.getName()).orElseThrow());
                    }
                }
            } else throw new NothingToAddException("No data to add in message");
        } else throw new NothingToAddException("This album does not exist");
    }

    public void deleteAll() {
        albumRepository.deleteAll();
    }

    public Collection<Album> fromDto(Collection<AlbumDto> dto) {
        if (dto == null) return null;
        Collection<Album> list = new ArrayList<>();
        for (AlbumDto album : dto) {
            Optional<Album> optionalAlbum = findByName(album.getName());
            if (optionalAlbum.isPresent()) {
                list.add(optionalAlbum.get());
            } else {
                Album tmpAlbum = new Album();
                tmpAlbum.setName(album.getName());
                list.add(albumRepository.save(tmpAlbum));
            }
        }
        return list;
    }
}
