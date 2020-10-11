package com.example;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.client.RestTemplate;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringJUnitConfig
@SpringBootTest(
        classes = MyApplication.class,
        webEnvironment = RANDOM_PORT,
        properties = {
                "spring.main.banner-mode=off",
                "baseUrl=http://my-base-url/"
        }
)
public class FirstIntegrationTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(FirstIntegrationTest.class);

    @Autowired
    private RestTemplate restTemplate;

    @LocalServerPort
    private int serverPort;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = serverPort;
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }

    @Test
    public void test() {
        LOGGER.info("restTemplate: {}", restTemplate);
    }
}
