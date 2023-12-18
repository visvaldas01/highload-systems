package ru.ifmo.highloadsystems.testcontainers.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.ifmo.highloadsystems.exception.AlreadyExistException;
import ru.ifmo.highloadsystems.model.dto.TagGroupDto;
import ru.ifmo.highloadsystems.service.TagGroupService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
public class TagGroupServiceTest {
    @Autowired
    private TagGroupService tagGroupService;

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
        tagGroupService.deleteAll();
    }

    @Test
    void addTest() {
        TagGroupDto tagGroupDto = new TagGroupDto();
        tagGroupDto.setName("TagGroup1");
        tagGroupService.add(tagGroupDto);
        Assertions.assertTrue(tagGroupService.findByName("TagGroup1").isPresent());
    }

    @Test
    void addExistingTest() {
        TagGroupDto tagGroupDto = new TagGroupDto();
        tagGroupDto.setName("TagGroup1");
        tagGroupService.add(tagGroupDto);
        Assertions.assertThrows(AlreadyExistException.class, () -> tagGroupService.add(tagGroupDto));
    }
}
