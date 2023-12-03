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
public class MusicianServiceTest {
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
        musicianService.add(new MusicianDto("Musician1"));
        Assertions.assertFalse(musicianService.findByName("Musician1").isEmpty());
    }
}
