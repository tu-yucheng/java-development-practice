package cn.tuyucheng.taketoday.springevents.synchronous;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SynchronousSpringEventsConfig.class}, loader = AnnotationConfigContextLoader.class)
class GenericAppEventListenerIntegrationTest {

    @Autowired
    private CustomSpringEventPublisher publisher;
    @Autowired
    private GenericSpringEventListener listener;

    @Test
    void testGenericSpringEvent() {
        assertFalse(listener.isHitEventHandler(), "The initial value should be false");
        publisher.publishGenericAppEvent("Hello world!!!");
        assertTrue(listener.isHitEventHandler(), "Now the value should be changed to true");
    }
}