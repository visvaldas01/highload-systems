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
import ru.ifmo.highloadsystems.service.*;

import java.util.ArrayList;
import java.util.Collection;

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.NONE)
@Testcontainers
public class TagServiceTest {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private SongService songService;
    @Autowired
    private TagService tagService;
    @Autowired
    private MusicianService musicianService;
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
        albumService.deleteAll();
        songService.deleteAll();
        tagService.deleteAll();
        tagGroupService.deleteAll();
        musicianService.deleteAll();
    }

    @Test
    void addTest()
    {
        TagDto tagDto = new TagDto("Tag1");
        tagDto.setTagGroup(new TagGroupDto("TagGroup1"));
        tagService.add(tagDto);
        Assertions.assertFalse(tagService.findByName("Tag1").isEmpty());
    }

    @Test
    void addExistingTest()
    {
        TagDto tagDto = new TagDto("Tag1");
        tagDto.setTagGroup(new TagGroupDto("TagGroup1"));
        tagService.add(tagDto);
        Assertions.assertFalse(tagService.findByName("Tag1").isEmpty());
        Assertions.assertThrows(AlreadyExistException.class, () -> tagService.add(tagDto));
    }

    @Test
    void addSongToTagTest()
    {
        TagDto tagDto = new TagDto("Tag1");
        tagDto.setTagGroup(new TagGroupDto("TagGroup1"));
        tagService.add(tagDto);
        Collection<SongDto> songDtoCollection = new ArrayList<SongDto>();
        songDtoCollection.add(new SongDto("Song1"));
        songDtoCollection.add(new SongDto("Song2"));
        tagDto.setSongs(songDtoCollection);
        tagService.addTag(tagDto);
        Assertions.assertFalse(tagService.findByName("Tag1").isEmpty());
        Assertions.assertFalse(songService.findByName("Song1").isEmpty());
        Assertions.assertFalse(songService.findByName("Song2").isEmpty());
        Assertions.assertEquals(2, tagService.findByName("Tag1").get().getSongs().size());
    }

    @Test
    void addMusicianToTagTest()
    {
        TagDto tagDto = new TagDto("Tag1");
        tagDto.setTagGroup(new TagGroupDto("TagGroup1"));
        tagService.add(tagDto);
        Collection<MusicianDto> musicianDtoCollection = new ArrayList<MusicianDto>();
        musicianDtoCollection.add(new MusicianDto("Musician1"));
        musicianDtoCollection.add(new MusicianDto("Musician2"));
        tagDto.setMusicians(musicianDtoCollection);
        tagService.addTag(tagDto);
        Assertions.assertFalse(tagService.findByName("Tag1").isEmpty());
        Assertions.assertFalse(musicianService.findByName("Musician1").isEmpty());
        Assertions.assertFalse(musicianService.findByName("Musician2").isEmpty());
        Assertions.assertEquals(2, tagService.findByName("Tag1").get().getMusicians().size());
    }

    @Test
    void nowhereToAddTest()
    {
        TagDto tagDto = new TagDto("Tag1");
        NothingToAddException ex = Assertions.assertThrows(NothingToAddException.class, ()->tagService.addTag(tagDto));
        Assertions.assertEquals("Tag not existing", ex.getMessage());
    }

    @Test
    void nothingToAddTest()
    {
        TagDto tagDto = new TagDto("Tag1");
        tagDto.setTagGroup(new TagGroupDto("TagGroup1"));
        tagService.add(tagDto);
        NothingToAddException ex = Assertions.assertThrows(NothingToAddException.class, ()->tagService.addTag(tagDto));
        Assertions.assertEquals("No data to add in message", ex.getMessage());
    }
}
