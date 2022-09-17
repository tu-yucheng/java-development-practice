package cn.tuyucheng.taketoday.stepverifier;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

class PostExecutionUnitTest {

    Flux<Integer> source = Flux.<Integer>create(emitter -> {
        emitter.next(1);
        emitter.next(2);
        emitter.next(3);
        emitter.complete();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            emitter.error(new RuntimeException(e));
        }
        emitter.next(4);
    }).filter(number -> number % 2 == 0);
    
    @Test
    void droppedElements () {
        StepVerifier.create(source)
                .expectNext(2)
                .expectComplete()
                .verifyThenAssertThat()
                .hasDropped(4)
                .tookLessThan(Duration.ofMillis(1500));
    }
}