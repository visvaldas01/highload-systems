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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RecommendationsTest {
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

    String auth(String username, String password) throws Exception {
        MvcResult result = mockMvc.perform(post("/auth/token").contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"" + username + "\", \"password\": \"" + password + "\" }")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        JSONObject object = new JSONObject(result.getResponse().getContentAsString());

        return object.getString("token");
    }

    @Test
    void recommendTest() throws Exception {
        String jwt = auth("Yaroslave", "ccc");

        mockMvc.perform(post("/songs").contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwt)
                        .content("{ \"name\": \"Song1\", \"musician\": [{ \"name\": \"Musician1\" }] }")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(post("/songs").contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwt)
                        .content("{ \"name\": \"Song2\", \"musician\": [{ \"name\": \"Blur\" }] }")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(post("/scrobbles").contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwt)
                        .content("{ \"song\": { \"name\": \"Song1\", \"musician\": [{ \"name\": \"Musician1\" }] }, \"date\": \"2023-11-14T16:56:31.515092\" }")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(post("/scrobbles").contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwt)
                        .content("{ \"song\": { \"name\": \"Song1\", \"musician\": [{ \"name\": \"Musician1\" }] }, \"date\": \"2023-11-14T16:56:32.515092\" }")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(post("/scrobbles").contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwt)
                        .content("{ \"song\": { \"name\": \"Song2\", \"musician\": [{ \"name\": \"Blur\" }] }, \"date\": \"2023-11-14T16:56:33.515092\" }")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/songs/recommendations").contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwt)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":1,\"name\":\"Dust My Broom\",\"vector1\":null,\"vector2\":null,\"vector3\":null,\"musicians\":[],\"tags\":[],\"users\":[{\"id\":1,\"username\":\"Irina\",\"password\":\"$2a$10$gh1NJRt7IXfmKb5fXP0pmOwSuTWwXcrFIENAdXi/2vCsFhZcerhVS\",\"roles\":[{\"id\":1,\"name\":\"ROLE_USER\"}],\"albums\":[],\"musicians\":[]}],\"albums\":[]}"));
    }
}
