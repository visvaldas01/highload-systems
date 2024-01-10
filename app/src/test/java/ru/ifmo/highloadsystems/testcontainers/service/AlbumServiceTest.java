package ru.ifmo.highloadsystems.testcontainers.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.ifmo.highloadsystems.exception.AlreadyExistException;
import ru.ifmo.highloadsystems.exception.NothingToAddException;
import ru.ifmo.highloadsystems.model.dto.*;
import ru.ifmo.highloadsystems.service.AlbumService;
import ru.ifmo.highloadsystems.service.MusicianService;
import ru.ifmo.highloadsystems.service.SongService;
import ru.ifmo.highloadsystems.service.TagService;

import java.util.ArrayList;
import java.util.Collection;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
public class AlbumServiceTest {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private SongService songService;
    @Autowired
    private TagService tagService;
    @Autowired
    private MusicianService musicianService;

    @Container
    @ServiceConnection
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    @AfterEach
    void clean() {
        albumService.deleteAll();
        songService.deleteAll();
        tagService.deleteAll();
        musicianService.deleteAll();
    }

    @Test
    void addTest() {
        AlbumDto albumDto = new AlbumDto();
        albumDto.setName("Album1");
        albumService.addNewAlbum(albumDto);
        Assertions.assertFalse(albumService.findByName("Album1").isEmpty());
    }

    @Test
    void addExistingTest() {
        AlbumDto albumDto = new AlbumDto();
        albumDto.setName("Album1");
        albumService.addNewAlbum(albumDto);
        Assertions.assertFalse(albumService.findByName("Album1").isEmpty());
        Assertions.assertThrows(AlreadyExistException.class, () -> albumService.addNewAlbum(albumDto));
    }

    @Test
    void addSongToAlbumTest() {
        AlbumDto albumDto = new AlbumDto();
        albumDto.setName("Album1");
        albumService.addNewAlbum(albumDto);
        Collection<SongDto> songDtoCollection = new ArrayList<>();
        SongDto songDto1 = new SongDto();
        songDto1.setName("Song1");
        SongDto songDto2 = new SongDto();
        songDto2.setName("Song2");
        songDtoCollection.add(songDto1);
        songDtoCollection.add(songDto2);
        albumDto.setSongs(songDtoCollection);
        albumService.addToAlbum(albumDto);
        Assertions.assertFalse(albumService.findByName("Album1").isEmpty());
        Assertions.assertFalse(songService.findByName("Song1").isEmpty());
        Assertions.assertFalse(songService.findByName("Song2").isEmpty());
        Assertions.assertEquals(2, albumService.findByName("Album1").get().getSongs().size());
    }

    @Test
    void addTagToAlbumTest() {
        AlbumDto albumDto = new AlbumDto();
        albumDto.setName("Album1");
        albumService.addNewAlbum(albumDto);
        Collection<TagDto> tagDtoCollection = new ArrayList<>();
        TagDto tag1 = new TagDto();
        tag1.setName("Tag1");
        TagGroupDto tagGroupDto1 = new TagGroupDto();
        tagGroupDto1.setName("Tag1Group");
        tag1.setTagGroup(tagGroupDto1);
        tagDtoCollection.add(tag1);
        TagDto tag2 = new TagDto();
        tag2.setName("Tag2");
        TagGroupDto tagGroupDto2 = new TagGroupDto();
        tagGroupDto2.setName("Tag2Group");
        tag2.setTagGroup(tagGroupDto2);
        tagDtoCollection.add(tag2);
        albumDto.setTags(tagDtoCollection);
        albumService.addToAlbum(albumDto);
        Assertions.assertFalse(albumService.findByName("Album1").isEmpty());
        Assertions.assertFalse(tagService.findByName("Tag1").isEmpty());
        Assertions.assertFalse(tagService.findByName("Tag2").isEmpty());
        Assertions.assertEquals(2, albumService.findByName("Album1").get().getTags().size());
    }

    @Test
    void addMusicianToAlbumTest() {
        AlbumDto albumDto = new AlbumDto();
        albumDto.setName("Album1");
        albumService.addNewAlbum(albumDto);
        Collection<MusicianDto> musicianDtoCollection = new ArrayList<>();
        MusicianDto musicianDto1 = new MusicianDto();
        musicianDto1.setName("Musician1");
        MusicianDto musicianDto2 = new MusicianDto();
        musicianDto2.setName("Musician2");
        musicianDtoCollection.add(musicianDto1);
        musicianDtoCollection.add(musicianDto2);
        albumDto.setMusicians(musicianDtoCollection);
        albumService.addToAlbum(albumDto);
        Assertions.assertFalse(albumService.findByName("Album1").isEmpty());
        Assertions.assertFalse(musicianService.findByName("Musician1").isEmpty());
        Assertions.assertFalse(musicianService.findByName("Musician2").isEmpty());
        Assertions.assertEquals(2, albumService.findByName("Album1").get().getMusicians().size());
    }

    @Test
    void nowhereToAddTest() {
        AlbumDto albumDto = new AlbumDto();
        albumDto.setName("Album1");
        NothingToAddException ex = Assertions.assertThrows(NothingToAddException.class, () -> albumService.addToAlbum(albumDto));
        Assertions.assertEquals("This album does not exist", ex.getMessage());
    }

    @Test
    void nothingToAddTest() {
        AlbumDto albumDto = new AlbumDto();
        albumDto.setName("Album1");
        albumService.addNewAlbum(albumDto);
        NothingToAddException ex = Assertions.assertThrows(NothingToAddException.class, () -> albumService.addToAlbum(albumDto));
        Assertions.assertEquals("No data to add in message", ex.getMessage());
    }
}
