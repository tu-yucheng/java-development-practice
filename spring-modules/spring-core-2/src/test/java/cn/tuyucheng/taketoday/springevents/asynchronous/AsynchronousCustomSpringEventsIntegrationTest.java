package cn.tuyucheng.taketoday.springevents.asynchronous;

import cn.tuyucheng.taketoday.springevents.synchronous.CustomSpringEventPublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AsynchronousSpringEventsConfig.class}, loader = AnnotationConfigContextLoader.class)
class AsynchronousCustomSpringEventsIntegrationTest {

    @Autowired
    private CustomSpringEventPublisher publisher;

    @Test
    void testCustomSpringEvents() {
        publisher.publishCustomEvent("Hello world!!");
        System.out.println("Done publishing asynchronous custom event. ");
    }
}