package ru.ifmo.highloadsystems.testcontainers.service;

import org.hibernate.Hibernate;
import org.hibernate.Session;
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
import ru.ifmo.highloadsystems.model.entity.Song;
import ru.ifmo.highloadsystems.service.AlbumService;
import ru.ifmo.highloadsystems.service.MusicianService;
import ru.ifmo.highloadsystems.service.SongService;
import ru.ifmo.highloadsystems.service.TagService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.NONE)
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
    void addTest()
    {
        albumService.addNewAlbum(new AlbumDto("Album1"));
        Assertions.assertFalse(albumService.findByName("Album1").isEmpty());
    }

    @Test
    void addExistingTest()
    {
        albumService.addNewAlbum(new AlbumDto("Album1"));
        Assertions.assertFalse(albumService.findByName("Album1").isEmpty());
        Assertions.assertThrows(AlreadyExistException.class, () -> albumService.addNewAlbum(new AlbumDto("Album1")));
    }

    @Test
    void addSongToAlbumTest()
    {
        AlbumDto albumDto = new AlbumDto("Album1");
        albumService.addNewAlbum(albumDto);
        Collection<SongDto> songDtoCollection = new ArrayList<SongDto>();
        songDtoCollection.add(new SongDto("Song1"));
        songDtoCollection.add(new SongDto("Song2"));
        albumDto.setSongs(songDtoCollection);
        albumService.addToAlbum(albumDto);
        Assertions.assertFalse(albumService.findByName("Album1").isEmpty());
        Assertions.assertFalse(songService.findByName("Song1").isEmpty());
        Assertions.assertFalse(songService.findByName("Song2").isEmpty());
        Assertions.assertEquals(2, albumService.findByName("Album1").get().getSongs().size());
    }

    @Test
    void addTagToAlbumTest()
    {
        AlbumDto albumDto = new AlbumDto("Album1");
        albumService.addNewAlbum(albumDto);
        Collection<TagDto> tagDtoCollection = new ArrayList<TagDto>();
        TagDto tag1 = new TagDto("Tag1");
        tag1.setTagGroup(new TagGroupDto("Tag1Group"));
        tagDtoCollection.add(tag1);
        TagDto tag2 = new TagDto("Tag2");
        tag2.setTagGroup(new TagGroupDto("Tag2Group"));
        tagDtoCollection.add(tag2);
        albumDto.setTags(tagDtoCollection);
        albumService.addToAlbum(albumDto);
        Assertions.assertFalse(albumService.findByName("Album1").isEmpty());
        Assertions.assertFalse(tagService.findByName("Tag1").isEmpty());
        Assertions.assertFalse(tagService.findByName("Tag2").isEmpty());
        Assertions.assertEquals(2, albumService.findByName("Album1").get().getTags().size());
    }

    @Test
    void addMusicianToAlbumTest()
    {
        AlbumDto albumDto = new AlbumDto("Album1");
        albumService.addNewAlbum(albumDto);
        Collection<MusicianDto> musicianDtoCollection = new ArrayList<MusicianDto>();
        musicianDtoCollection.add(new MusicianDto("Musician1"));
        musicianDtoCollection.add(new MusicianDto("Musician2"));
        albumDto.setMusicians(musicianDtoCollection);
        albumService.addToAlbum(albumDto);
        Assertions.assertFalse(albumService.findByName("Album1").isEmpty());
        Assertions.assertFalse(musicianService.findByName("Musician1").isEmpty());
        Assertions.assertFalse(musicianService.findByName("Musician2").isEmpty());
        Assertions.assertEquals(2, albumService.findByName("Album1").get().getMusicians().size());
    }

    @Test
    void nowhereToAddTest()
    {
        AlbumDto albumDto = new AlbumDto("Album1");
        NothingToAddException ex = Assertions.assertThrows(NothingToAddException.class, ()->albumService.addToAlbum(albumDto));
        Assertions.assertEquals("Album not existing", ex.getMessage());
    }

    @Test
    void nothingToAddTest()
    {
        AlbumDto albumDto = new AlbumDto("Album1");
        albumService.addNewAlbum(albumDto);
        NothingToAddException ex = Assertions.assertThrows(NothingToAddException.class, ()->albumService.addToAlbum(albumDto));
        Assertions.assertEquals("No data to add in message", ex.getMessage());
    }
}
