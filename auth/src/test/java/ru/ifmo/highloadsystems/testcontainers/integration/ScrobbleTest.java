//package ru.ifmo.highloadsystems.testcontainers.integration;
//
//import org.json.JSONObject;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@Testcontainers
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//public class ScrobbleTest {
//    @Container
//    @ServiceConnection
//    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");
//
//    @Autowired
//    private MockMvc mockMvc;
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
//    String auth(String username, String password) throws Exception {
//        MvcResult result = mockMvc.perform(post("/auth/token").contentType(MediaType.APPLICATION_JSON)
//                        .content("{ \"username\": \"" + username + "\", \"password\": \"" + password + "\" }")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk()).andReturn();
//
//        JSONObject object = new JSONObject(result.getResponse().getContentAsString());
//
//        return object.getString("token");
//    }
//
//    void reg(String username, String password) throws Exception {
//        String jwt = auth("Yaroslave", "ccc");
//        mockMvc.perform(post("/auth/registration").contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + jwt)
//                        .content("{ \"username\": \"" + username + "\", \"password\": \"" + password + "\", \"confirmPassword\": \"" + password + "\" }")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void addScrobbleTest() throws Exception {
//        reg("user1", "abacab");
//        String jwt = auth("user1", "abacab");
//        mockMvc.perform(post("/scrobbles").contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + jwt)
//                        .content("{ \"song\": { \"name\": \"Rooster\", \"musician\": [{ \"name\": \"Alice in Chains\" }] }, \"date\": \"2023-11-14T16:56:30.515092\" }")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void addScrobbleNotLoginTest() throws Exception {
//        mockMvc.perform(post("/scrobbles").contentType(MediaType.APPLICATION_JSON)
//                        .content("{ \"song\": { \"name\": \"Rooster\", \"musician\": [{ \"name\": \"Alice in Chains\" }] }, \"date\": \"2023-11-14T16:56:30.515092\" }")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().is(401));
//    }
//
//    @Test
//    void getAllTest() throws Exception {
//        reg("user2", "abacab");
//        String jwt = auth("user2", "abacab");
//        mockMvc.perform(post("/scrobbles").contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + jwt)
//                        .content("{\"date\": \"2023-11-14T16:56:30.515092\", \"song\": {\"name\": \"Song1\", \"musician\": [{ \"name\": \"Musician1\" }]}}")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        mockMvc.perform(post("/scrobbles").contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + jwt)
//                        .content("{\"date\": \"2023-11-14T16:56:30.515092\", \"song\": {\"name\": \"Song2\", \"musician\": [{ \"name\": \"Blur\" }]}}")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        mockMvc.perform(post("/scrobbles").contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + jwt)
//                        .content("{\"date\": \"2023-11-14T16:56:30.515092\", \"song\": {\"name\": \"Song3\", \"musician\": [{ \"name\": \"Musician3\" }]}}")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        mockMvc.perform(get("/scrobbles").contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + jwt)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void getStatsTest() throws Exception {
//        reg("user3", "abacab");
//        String jwt = auth("user3", "abacab");
//        mockMvc.perform(post("/scrobbles").contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + jwt)
//                        .content("{ \"song\": { \"name\": \"Song1\", \"musician\": [{ \"name\": \"Musician1\" }] }, \"date\": \"2023-11-14T16:56:30.515092\" }")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        mockMvc.perform(post("/scrobbles").contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + jwt)
//                        .content("{ \"song\": { \"name\": \"Song2\", \"musician\": [{ \"name\": \"Blur\" }] }, \"date\": \"2023-11-14T16:57:30.515092\" }")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        mockMvc.perform(post("/scrobbles").contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + jwt)
//                        .content("{ \"song\": { \"name\": \"Song1\", \"musician\": [{ \"name\": \"Musician1\" }] }, \"date\": \"2023-11-14T16:58:30.515092\" }")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        mockMvc.perform(get("/scrobbles/stats").contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + jwt)
//                        .content("{\"username\": \"user3\", \"requestTarget\": \"Song\", \"size\": \"3\"}")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(content().json("{\"nums\":[1,2],\"names\":[\"Song2\",\"Song1\"]}"));
//    }
//
//    @Test
//    void getNoAuthTest() throws Exception {
//        reg("user4", "abacab");
//        String jwt = auth("user4", "abacab");
//        mockMvc.perform(post("/scrobbles").contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + jwt)
//                        .content("{\"date\": \"2023-11-14T16:56:31.515092\", \"song\": {\"name\": \"Song1\", \"musician\": [{ \"name\": \"Musician1\" }]}}")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        mockMvc.perform(post("/scrobbles").contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + jwt)
//                        .content("{\"date\": \"2023-11-14T16:56:32.515092\", \"song\": {\"name\": \"Song1\", \"musician\": [{ \"name\": \"Musician1\" }]}}")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        mockMvc.perform(post("/scrobbles").contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + jwt)
//                        .content("{\"date\": \"2023-11-14T16:56:33.515092\", \"song\": {\"name\": \"Song2\", \"musician\": [{ \"name\": \"Blur\" }]}}")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        mockMvc.perform(get("/scrobbles/stats").contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"requestTarget\": \"Song\", \"size\": \"3\"}")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().is(423));
//    }
//
//    @Test
//    void getStatsOnlyAuthTest() throws Exception {
//        reg("user5", "abacab");
//        String jwt = auth("user5", "abacab");
//        mockMvc.perform(post("/scrobbles").contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + jwt)
//                        .content("{\"date\": \"2023-11-14T16:56:31.515092\", \"song\": {\"name\": \"Song1\", \"musician\": [{ \"name\": \"Musician1\" }]}}")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        mockMvc.perform(post("/scrobbles").contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + jwt)
//                        .content("{\"date\": \"2023-11-14T16:56:32.515092\", \"song\": {\"name\": \"Song1\", \"musician\": [{ \"name\": \"Musician1\" }]}}")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        mockMvc.perform(post("/scrobbles").contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + jwt)
//                        .content("{\"date\": \"2023-11-14T16:56:33.515092\", \"song\": {\"name\": \"Song2\", \"musician\": [{ \"name\": \"Blur\" }]}}")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        mockMvc.perform(get("/scrobbles/stats").contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + jwt)
//                        .content("{\"requestTarget\": \"Song\", \"size\": \"3\"}")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(content().json("{\"nums\":[1,2],\"names\":[\"Song2\",\"Song1\"]}"));
//    }
//
//    @Test
//    void getStatsNotImplementedTest() throws Exception {
//        reg("user6", "abacab");
//        String jwt = auth("user6", "abacab");
//        mockMvc.perform(get("/scrobbles/stats").contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + jwt)
//                        .content("{\"requestTarget\": \"Random\", \"size\": \"3\"}")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().is(501));
//    }
//
//    @Test
//    void getStatsAlbumTest() throws Exception {
//        reg("user7", "abacab");
//        String jwt = auth("user7", "abacab");
//        mockMvc.perform(post("/scrobbles").contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + jwt)
//                        .content("{\"date\": \"2023-11-14T16:56:31.515092\", \"song\": {\"name\": \"Song1.7\", \"musician\": [{ \"name\": \"Musician1\"}], \"album\": [{\"name\": \"Album2\"}]}}")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        mockMvc.perform(post("/scrobbles").contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + jwt)
//                        .content("{\"date\": \"2023-11-14T16:56:31.515092\", \"song\": {\"name\": \"Song1.7\", \"musician\": [{ \"name\": \"Musician1\" }]}}")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        mockMvc.perform(post("/scrobbles").contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + jwt)
//                        .content("{\"date\": \"2023-11-14T16:56:33.515092\", \"song\": {\"name\": \"Song2.7\", \"musician\": [{ \"name\": \"Musician1\"}], \"album\": [{\"name\": \"Album1\"}]}}")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        mockMvc.perform(get("/scrobbles/stats").contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + jwt)
//                        .content("{\"requestTarget\": \"Album\", \"size\": \"3\"}")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(content().json("{\"nums\":[2,1],\"names\":[\"Album2\",\"Album1\"]}"));
//    }
//
//    @Test
//    void getStatsMusicianTest() throws Exception {
//        reg("user8", "abacab");
//        String jwt = auth("user8", "abacab");
//        mockMvc.perform(post("/scrobbles").contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + jwt)
//                        .content("{\"date\": \"2023-11-14T16:56:31.515092\", \"song\": {\"name\": \"Song1.8\", \"musician\": [{\"name\": \"Musician2\"}]}}")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        mockMvc.perform(post("/scrobbles").contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + jwt)
//                        .content("{\"date\": \"2023-11-14T16:56:32.515092\", \"song\": {\"name\": \"Song1.8\", \"musician\": [{\"name\": \"Musician2\"}]}}")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        mockMvc.perform(post("/scrobbles").contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + jwt)
//                        .content("{\"date\": \"2023-11-14T16:56:32.515092\", \"song\": {\"name\": \"Song1.8\"}}")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest());
//
//        mockMvc.perform(post("/scrobbles").contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + jwt)
//                        .content("{\"date\": \"2023-11-14T16:56:33.515092\", \"song\": {\"name\": \"Song2.8\",\"musician\": [{\"name\": \"Musician1\"}]}}")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        mockMvc.perform(get("/scrobbles/stats").contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + jwt)
//                        .content("{\"requestTarget\": \"Musician\", \"size\": \"3\"}")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(content().json("{\"nums\":[2,1],\"names\":[\"Musician2\",\"Musician1\"]}"));
//    }
//
//    @Test
//    void getStatsTagTest() throws Exception {
//        reg("user9", "abacab");
//        String jwt = auth("user9", "abacab");
//        mockMvc.perform(post("/scrobbles").contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + jwt)
//                        .content("{\"date\": \"2023-11-14T16:56:31.515092\", \"song\": {\"name\": \"Song1.9\", \"musician\": [{\"name\": \"Musician2\"}], \"tag\": [{\"name\": \"Tag2\", \"tagGroup\": {\"name\": \"TagGroup\"}}]}}")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        mockMvc.perform(post("/scrobbles").contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + jwt)
//                        .content("{\"date\": \"2023-11-14T16:56:32.515092\", \"song\": {\"name\": \"Song1.9\", \"musician\": [{\"name\": \"Musician2\"}]}}")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        mockMvc.perform(post("/scrobbles").contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + jwt)
//                        .content("{\"date\": \"2023-11-14T16:56:33.515092\", \"song\": {\"name\": \"Song2.9\", \"musician\": [{\"name\": \"Musician2\"}], \"tag\": [{\"name\": \"Tag1\", \"tagGroup\": {\"name\": \"TagGroup\"}}]}}")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        mockMvc.perform(get("/scrobbles/stats").contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + jwt)
//                        .content("{\"requestTarget\": \"Tag\", \"size\": \"3\"}")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(content().json("{\"nums\":[2,1],\"names\":[\"Tag2\",\"Tag1\"]}"));
//    }
//}
