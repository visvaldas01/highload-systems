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
import ru.ifmo.highloadsystems.service.SongService;

import java.util.ArrayList;
import java.util.Collection;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
public class SongServiceTest {
    @Autowired
    private SongService songService;

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
        songService.deleteAll();
    }

    @Test
    void addTest() {
        SongDto songDto1 = new SongDto();
        songDto1.setName("Song1");
        songService.add(songDto1);
        Assertions.assertFalse(songService.findByName("Song1").isEmpty());
    }

    @Test
    void addExistingTest() {
        SongDto songDto1 = new SongDto();
        songDto1.setName("Song1");
        songService.add(songDto1);
        Assertions.assertFalse(songService.findByName("Song1").isEmpty());
        Assertions.assertThrows(AlreadyExistException.class, () -> songService.add(songDto1));
    }

    @Test
    void nowhereToAddTest() {
        SongDto songDto = new SongDto();
        songDto.setName("Song1");
        NothingToAddException ex = Assertions.assertThrows(NothingToAddException.class, () -> songService.addTo("", songDto));
        Assertions.assertEquals("This song does not exist", ex.getMessage());
    }

    @Test
    void nothingToAddTest() {
        SongDto songDto = new SongDto();
        songDto.setName("Song1");
        songService.add(songDto);
        NothingToAddException ex = Assertions.assertThrows(NothingToAddException.class, () -> songService.addTo("", songDto));
        Assertions.assertEquals("No data to add in song", ex.getMessage());
    }
}
