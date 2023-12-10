package ru.ifmo.highloadsystems.testcontainers.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.ifmo.highloadsystems.exception.AlreadyExistException;
import ru.ifmo.highloadsystems.exception.NoPermissionException;
import ru.ifmo.highloadsystems.model.dto.*;
import ru.ifmo.highloadsystems.service.*;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.NONE)
@Testcontainers
public class SrcobbleServiceTest {
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
    @Autowired
    private AuthService authService;

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
    void addNotPermissionTest()
    {
        ScrobbleDto scrobbleDto = new ScrobbleDto(new SongDto("Song1"), LocalDateTime.now());
        Assertions.assertThrows(NullPointerException.class,() -> scrobbleService.addScrobble(scrobbleDto));
    }
}
