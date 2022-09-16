package cn.tuyucheng.taketoday.reactive.webclient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = WebClientApplication.class)
public class WebControllerIntegrationTest {
    @Autowired
    private WebTestClient testClient;

    @LocalServerPort
    private int randomServerPort;

    @Autowired
    private WebController webController;

    @BeforeEach
    void setUp() {
        webController.setServerPort(randomServerPort);
    }

    @Test
    void whenEndpointWithBlockingClientIsCalled_thenThreeTweetsAreReceived() {
        testClient.get()
                .uri("/tweets-blocking")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Tweet.class).hasSize(3);
    }

    @Test
    void whenEndpointWithNonBlockingClientIsCalled_ThenThreeTweetsAreReceived() {
        testClient.get()
                .uri("/tweets-non-blocking")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Tweet.class).hasSize(3);
    }
}