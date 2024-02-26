package ru.ifmo.highloadsystems.testcontainers.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.security.authentication.BadCredentialsException;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.ifmo.highloadsystems.model.dto.JwtRequest;
import ru.ifmo.highloadsystems.model.dto.JwtResponse;
import ru.ifmo.highloadsystems.model.dto.RegistrationUserDto;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
public class AuthServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;
    @Autowired
    private JwtTokensUtils jwtTokensUtils;

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
        userService.deleteAll();
    }

    @Test
    void validAuthTest() {
        String username = "username";
        String password = "password";
        Assertions.assertDoesNotThrow(() -> authService.createNewUser(new RegistrationUserDto(username, password, password)));
        JwtResponse jwtResponse = (JwtResponse) authService.createAuthToken(new JwtRequest(username, password)).getBody();
        assert jwtResponse != null;
        Assertions.assertEquals(username, jwtTokensUtils.getUsername(jwtResponse.token()));
        Assertions.assertEquals(List.of("ROLE_USER"), jwtTokensUtils.getRoles(jwtResponse.token()));
    }

    @ParameterizedTest
    @CsvSource({
            "username, password, username, wrongPassword",
            "username, password, wrongUsername, password",
            "username, password, wrongUsername, wrongPassword"
    })
    void invalidPasswordAuthTest(String regUsername, String regPassword, String authUsername, String authPassword) {
        Assertions.assertEquals(regUsername, authService.createNewUser(new RegistrationUserDto(regUsername, regPassword, regPassword)).getBody());
        Assertions.assertThrows(BadCredentialsException.class, () -> authService.createAuthToken(new JwtRequest(authUsername, authPassword)));
    }
}
