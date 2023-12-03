package ru.ifmo.highloadsystems.testcontainers.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.ifmo.highloadsystems.exception.NothingToAddException;
import ru.ifmo.highloadsystems.model.dto.*;
import ru.ifmo.highloadsystems.service.AlbumService;
import ru.ifmo.highloadsystems.service.MusicianService;
import ru.ifmo.highloadsystems.service.SongService;
import ru.ifmo.highloadsystems.service.TagService;

import java.util.ArrayList;
import java.util.Collection;

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.NONE)
@Testcontainers
public class SongServiceTest {
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
        songService.add(new SongDto("Song1"));
        Assertions.assertFalse(songService.findByName("Song1").isEmpty());
    }

    @Test
    void addMusicianToSongTest()
    {
        SongDto songDto = new SongDto("Song1");
        songService.add(songDto);
        Collection<MusicianDto> musicianDtoCollection = new ArrayList<MusicianDto>();
        musicianDtoCollection.add(new MusicianDto("Musician1"));
        musicianDtoCollection.add(new MusicianDto("Musician2"));
        songDto.setMusician(musicianDtoCollection);
        songService.addTo(songDto);
        Assertions.assertFalse(songService.findByName("Song1").isEmpty());
        Assertions.assertFalse(musicianService.findByName("Musician1").isEmpty());
        Assertions.assertFalse(musicianService.findByName("Musician2").isEmpty());
        Assertions.assertEquals(2, songService.findByName("Song1").get().getMusicians().size());
    }

    @Test
    void nowhereToAddTest()
    {
        SongDto songDto = new SongDto("Song1");
        NothingToAddException ex = Assertions.assertThrows(NothingToAddException.class, ()->songService.addTo(songDto));
        Assertions.assertEquals("Song not existing", ex.getMessage());
    }

    @Test
    void nothingToAddTest()
    {
        SongDto songDto = new SongDto("Song1");
        songService.add(songDto);
        NothingToAddException ex = Assertions.assertThrows(NothingToAddException.class, ()->songService.addTo(songDto));
        Assertions.assertEquals("No data to add in song", ex.getMessage());
    }
}
