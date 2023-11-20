package ru.ifmo.highloadsystems.testcontainers;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.ifmo.highloadsystems.model.entity.User;
import ru.ifmo.highloadsystems.repository.UserRepository;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class ControllersTest {
    @Autowired
    UserRepository userRepository;

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

    @Test
    public void whenAuthTest() {
        User u1 = new User();
        u1.setLogin("abcd");
        u1.setPassword("123123");
        userRepository.save(u1);
        List<User> users = userRepository.findAll();
        User u2 = users.get(users.size() - 1);
        Assert.assertEquals(u1.getLogin(), u2.getLogin());
        Assert.assertEquals(u1.getPassword(), u2.getPassword());
    }
}

