package ru.ifmo.highloadsystems.testcontainers.integration;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AuthTest {
    @Container
    @ServiceConnection
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    String auth(String username) throws Exception {
        mockMvc.perform(post("/registration").contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"" + username + "\", \"password\": \"1234\", \"confirmPassword\": \"1234\" }")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        MvcResult result = mockMvc.perform(post("/auth").contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"" + username + "\", \"password\": \"1234\" }")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        JSONObject object = new JSONObject(result.getResponse().getContentAsString());
        String authToken = object.getString("token");

        return authToken;
    }

    @Test
    void justAuthTest() throws Exception {
        auth("user1");
    }

    @Test
    void createSameUsernameTest() throws Exception {
        auth("userSame");

        mockMvc.perform(post("/registration").contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"userSame\", \"password\": \"1234\", \"confirmPassword\": \"1234\" }")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    void notEqualPasswordTest() throws Exception {
        mockMvc.perform(post("/registration").contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"userSame\", \"password\": \"12345\", \"confirmPassword\": \"1234\" }")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }
}
