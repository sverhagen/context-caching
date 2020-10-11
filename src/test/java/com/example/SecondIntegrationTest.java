package com.example;

import com.example.service.SomeOtherComponent;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static io.restassured.RestAssured.expect;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringJUnitConfig
@SpringBootTest(
        classes = MyApplication.class,
        webEnvironment = RANDOM_PORT,
        properties = {
                "spring.main.banner-mode=off",
                "baseUrl=http://my-base-url/"
        }
)
public class SecondIntegrationTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecondIntegrationTest.class);

    @Autowired
    private RestTemplate restTemplate;

    @LocalServerPort
    private int serverPort;

    // this field causes havoc
    @MockBean
    private SomeOtherComponent someOtherComponent;

    @BeforeEach
    public void setup() {
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
        MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
        server.expect(requestTo("http://my-base-url/api/test"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess());

        expect().statusCode(200)
                .when().post("/api/my-controller");

        server.verify();
    }
}
