package cn.tuyucheng.taketoday.reactive.urlmatch;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.server.WebServer;
import org.springframework.test.web.reactive.server.WebTestClient;

class ExploreSpring5URLPatternUsingRouterFunctionsIntegrationTest {
    private static WebTestClient client;
    private static WebServer server;

    @BeforeAll
    public static void setup() throws Exception {
        server = new ExploreSpring5URLPatternUsingRouterFunctions().start();
        client = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + server.getPort())
                .build();
    }

    @AfterAll
    static void destroy() {
        server.stop();
    }

    @Test
    void givenRouter_whenGetPathWithSingleCharWildcard_thenGotPathPattern() {
        client.get()
                .uri("/test")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .isEqualTo("Path /t?st is accessed");
    }

    @Test
    void givenRouter_whenMultipleURIVariablePattern_thenGotPathVariable() {
        client.get()
                .uri("/test/ab/cd")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .isEqualTo("/ab/cd");
    }

    @Test
    void givenRouter_whenGetMultipleCharWildcard_thenGotPathPattern() {
        client.get()
                .uri("/tuyucheng/tutorialId")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .isEqualTo("/tuyucheng/*Id path was accessed");
    }

    @Test
    void givenRouter_whenGetMultiplePathVariableInSameSegment_thenGotPathVariable() {
        client.get()
                .uri("/tuyucheng_tutorial")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .isEqualTo("tuyucheng , tutorial");
    }

    @Test
    void givenRouter_whenGetRegexInPathVariable_thenGotPathVariable() {
        client.get()
                .uri("/abcd")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .isEqualTo("/{tuyucheng:[a-z]+} was accessed and tuyucheng=abcd");

        client.get()
                .uri("/1234")
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

    @Test
    void givenResources_whenAccess_thenGot() {
        client.get()
                .uri("/files/test/test.txt")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .isEqualTo("test");

        client.get()
                .uri("/files/hello.txt")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .isEqualTo("hello");
    }

    @Test
    void givenRouter_whenAccess_thenGot() {
        client.get()
                .uri("/resources/test/test.txt")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .isEqualTo("test");
    }
}