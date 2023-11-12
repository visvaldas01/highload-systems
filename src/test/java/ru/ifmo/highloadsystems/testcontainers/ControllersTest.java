package ru.ifmo.highloadsystems.testcontainers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class ControllersTest {
    @LocalServerPort
    private Integer port;

    @Container
    //TODO: тут должно быть имя dockerimagefile
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("")
            .withReuse(true)
            .withDatabaseName("postgresql");

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "CONTAINER.USERNAME=" + postgreSQLContainer.getUsername(),
                    "CONTAINER.PASSWORD=" + postgreSQLContainer.getPassword(),
                    "CONTAINER.URL=" + postgreSQLContainer.getJdbcUrl()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }
    private RequestSpecification whenAuth() {
        return
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .auth()
                        .preemptive()
                        .oauth2(
                                given()
                                        .contentType(ContentType.JSON)
                                        .when()
                                        .auth()
                                        .preemptive()
                                        .basic(postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword())
                                        .post("/login")
                                        .then()
                                        .statusCode(200)
                                        .extract()
                                        .response()
                                        .getBody()
                                        .asString());
    }

}

