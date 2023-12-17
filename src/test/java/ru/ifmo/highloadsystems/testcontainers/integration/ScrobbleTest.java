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
public class ScrobbleTest {
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
    void addScrobbleTest() throws Exception {
        String jwt = auth("user1");
        mockMvc.perform(post("/scrobbles/add").contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwt)
                        .content("{\"date\": \"2023-11-14T16:56:30.515092\", \"song\": {\"name\": \"Rooster\"}}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void addScrobbleNotLoginTest() throws Exception {
        mockMvc.perform(post("/scrobbles/add").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"date\": \"2023-11-14T16:56:30.515092\", \"song\": {\"name\": \"Rooster\"}}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));
    }

    @Test
    void getAllTest() throws Exception {
        String jwt = auth("user2");
        mockMvc.perform(post("/scrobbles/add").contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwt)
                        .content("{\"date\": \"2023-11-14T16:56:30.515092\", \"song\": {\"name\": \"Song1\"}}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(post("/scrobbles/add").contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwt)
                        .content("{\"date\": \"2023-11-14T16:56:30.515092\", \"song\": {\"name\": \"Song2\"}}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(post("/scrobbles/add").contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwt)
                        .content("{\"date\": \"2023-11-14T16:56:30.515092\", \"song\": {\"name\": \"Song3\"}}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/scrobbles/all").contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwt)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getStatsTest() throws Exception {
        String jwt = auth("user3");
        mockMvc.perform(post("/scrobbles/add").contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwt)
                        .content("{\"date\": \"2023-11-14T16:56:31.515092\", \"song\": {\"name\": \"Song1\"}}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(post("/scrobbles/add").contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwt)
                        .content("{\"date\": \"2023-11-14T16:56:32.515092\", \"song\": {\"name\": \"Song1\"}}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(post("/scrobbles/add").contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwt)
                        .content("{\"date\": \"2023-11-14T16:56:33.515092\", \"song\": {\"name\": \"Song2\"}}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/scrobbles/get_stat").contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwt)
                        .content("{\"username\": \"user3\", \"requestTarget\": \"Song\", \"size\": \"3\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"nums\":[1,2],\"names\":[\"Song2\",\"Song1\"]}"));
    }
}
