package cn.tuyucheng.taketoday.staticcontent;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StaticContentDefaultLocationIntegrationTest {
    @Autowired
    private WebTestClient client;

    @Test
    void whenRequestingHtmlFileFromDefaultLocation_thenReturnThisFileAndTextHtmlContentType() {
        client.get()
                .uri("/index.html")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE);
    }

    @Test
    void whenRequestingPngImageFromImgLocation_thenReturnThisFileAndImagePngContentType() {
        client.get()
                .uri("/img/static-image.jpg")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE);
    }

    @Test
    void whenRequestingMissingStaticResource_thenReturnNotFoundStatus() {
        client.get()
                .uri("/unknown.png")
                .exchange()
                .expectStatus().isNotFound();
    }
}