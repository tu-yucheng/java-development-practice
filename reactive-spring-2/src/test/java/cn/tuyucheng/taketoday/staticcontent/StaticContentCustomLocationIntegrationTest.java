package cn.tuyucheng.taketoday.staticcontent;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("assets-custom-location")
class StaticContentCustomLocationIntegrationTest {
    @Autowired
    private WebTestClient client;

    @Test
    void whenRequestingHtmlFileFromCustomLocation_thenReturnThisFileAndTextHtmlContentType() {
        client.get()
                .uri("/assets/index.html")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE);
    }

    @Test
    void whenRequestingMissingStaticResource_thenReturnNotFoundStatus() {
        client.get()
                .uri("/assets/unknown.png")
                .exchange()
                .expectStatus().isNotFound();
    }
}