//package ru.ifmo.highloadsystems.testcontainers.service;
//
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//import ru.ifmo.highloadsystems.exception.AlreadyExistException;
//import ru.ifmo.highloadsystems.exception.NothingToAddException;
//import ru.ifmo.highloadsystems.model.dto.*;
//import ru.ifmo.highloadsystems.service.*;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@Testcontainers
//public class TagServiceTest {
//    @Autowired
//    private AlbumService albumService;
//    @Autowired
//    private SongService songService;
//    @Autowired
//    private TagService tagService;
//    @Autowired
//    private MusicianService musicianService;
//    @Autowired
//    private TagGroupService tagGroupService;
//
//    @Container
//    @ServiceConnection
//    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");
//
//    @BeforeAll
//    static void beforeAll() {
//        postgreSQLContainer.start();
//    }
//
//    @AfterAll
//    static void afterAll() {
//        postgreSQLContainer.stop();
//    }
//
//    @AfterEach
//    void clean() {
//        albumService.deleteAll();
//        songService.deleteAll();
//        tagService.deleteAll();
//        tagGroupService.deleteAll();
//        musicianService.deleteAll();
//    }
//
//    @Test
//    void addTest() {
//        TagDto tagDto = new TagDto();
//        tagDto.setName("Tag1");
//        TagGroupDto tagGroupDto = new TagGroupDto();
//        tagGroupDto.setName("TagGroup1");
//        tagDto.setTagGroup(tagGroupDto);
//        tagService.add(tagDto);
//        Assertions.assertFalse(tagService.findByName("Tag1").isEmpty());
//    }
//
//    @Test
//    void addExistingTest() {
//        TagDto tagDto = new TagDto();
//        tagDto.setName("Tag1");
//        TagGroupDto tagGroupDto = new TagGroupDto();
//        tagGroupDto.setName("TagGroup1");
//        tagDto.setTagGroup(tagGroupDto);
//        tagService.add(tagDto);
//        Assertions.assertFalse(tagService.findByName("Tag1").isEmpty());
//        Assertions.assertThrows(AlreadyExistException.class, () -> tagService.add(tagDto));
//    }
//
//    @Test
//    void addSongToTagTest() {
//        TagDto tagDto = new TagDto();
//        tagDto.setName("Tag1");
//        TagGroupDto tagGroupDto = new TagGroupDto();
//        tagGroupDto.setName("TagGroup1");
//        tagDto.setTagGroup(tagGroupDto);
//        tagService.add(tagDto);
//        Collection<SongDto> songDtoCollection = new ArrayList<>();
//        SongDto song1 = new SongDto();
//        song1.setName("Song1");
//        SongDto song2 = new SongDto();
//        song2.setName("Song2");
//        songDtoCollection.add(song1);
//        songDtoCollection.add(song2);
//        tagDto.setSongs(songDtoCollection);
//        tagService.addTag(tagDto);
//        Assertions.assertFalse(tagService.findByName("Tag1").isEmpty());
//        Assertions.assertFalse(songService.findByName("Song1").isEmpty());
//        Assertions.assertFalse(songService.findByName("Song2").isEmpty());
//        Assertions.assertEquals(2, tagService.findByName("Tag1").get().getSongs().size());
//    }
//
//    @Test
//    void addMusicianToTagTest() {
//        TagDto tagDto = new TagDto();
//        tagDto.setName("Tag1");
//        TagGroupDto tagGroupDto = new TagGroupDto();
//        tagGroupDto.setName("TagGroup1");
//        tagDto.setTagGroup(tagGroupDto);
//        tagService.add(tagDto);
//        Collection<MusicianDto> musicianDtoCollection = new ArrayList<>();
//        MusicianDto musicianDto1 = new MusicianDto();
//        musicianDto1.setName("Musician1");
//        MusicianDto musicianDto2 = new MusicianDto();
//        musicianDto2.setName("Musician2");
//        musicianDtoCollection.add(musicianDto1);
//        musicianDtoCollection.add(musicianDto2);
//        tagDto.setMusicians(musicianDtoCollection);
//        tagService.addTag(tagDto);
//        Assertions.assertFalse(tagService.findByName("Tag1").isEmpty());
//        Assertions.assertFalse(musicianService.findByName("Musician1").isEmpty());
//        Assertions.assertFalse(musicianService.findByName("Musician2").isEmpty());
//        Assertions.assertEquals(2, tagService.findByName("Tag1").get().getMusicians().size());
//    }
//
//    @Test
//    void nowhereToAddTest() {
//        TagDto tagDto = new TagDto();
//        tagDto.setName("Tag1");
//        NothingToAddException ex = Assertions.assertThrows(NothingToAddException.class, () -> tagService.addTag(tagDto));
//        Assertions.assertEquals("This tag does not exist", ex.getMessage());
//    }
//
//    @Test
//    void nothingToAddTest() {
//        TagDto tagDto = new TagDto();
//        tagDto.setName("Tag1");
//        TagGroupDto tagGroupDto = new TagGroupDto();
//        tagGroupDto.setName("TagGroup1");
//        tagDto.setTagGroup(tagGroupDto);
//        tagService.add(tagDto);
//        NothingToAddException ex = Assertions.assertThrows(NothingToAddException.class, () -> tagService.addTag(tagDto));
//        Assertions.assertEquals("No data to add in message", ex.getMessage());
//    }
//}
