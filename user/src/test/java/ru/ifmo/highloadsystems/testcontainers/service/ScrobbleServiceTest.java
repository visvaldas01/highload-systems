package ru.ifmo.highloadsystems.testcontainers.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.ifmo.highloadsystems.model.dto.*;
import ru.ifmo.highloadsystems.service.*;
import ru.ifmo.user.model.dto.ScrobbleDto;
import ru.ifmo.user.model.dto.SongDto;
import ru.ifmo.user.service.*;

import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
public class ScrobbleServiceTest {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private SongService songService;
    @Autowired
    private TagService tagService;
    @Autowired
    private MusicianService musicianService;
    @Autowired
    private ScrobbleService scrobbleService;

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
        scrobbleService.deleteAll();
    }

    @Test
    void addNotPermissionTest() {
        SongDto songDto = new SongDto();
        songDto.setName("Song1");
        ScrobbleDto scrobbleDto = new ScrobbleDto();
        scrobbleDto.setSong(songDto);
        scrobbleDto.setDate(LocalDateTime.now());
        Assertions.assertThrows(NullPointerException.class, () -> scrobbleService.addScrobble(scrobbleDto));
    }
}
