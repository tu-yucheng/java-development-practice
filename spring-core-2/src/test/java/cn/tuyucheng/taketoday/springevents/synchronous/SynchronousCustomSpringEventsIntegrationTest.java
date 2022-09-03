package cn.tuyucheng.taketoday.springevents.synchronous;

import org.junit.jupiter.api.Disabled;
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
class SynchronousCustomSpringEventsIntegrationTest {
    @Autowired
    private CustomSpringEventPublisher publisher;
    @Autowired
    private AnnotationDrivenEventListener listener;

    @Test
    void testCustomSpringEvents() {
        assertFalse(listener.isHitCustomEventHandler(), "The value should be false");
        publisher.publishCustomEvent("Hello world!!");
        System.out.println("Done publishing synchronous custom event. ");
        assertTrue(listener.isHitCustomEventHandler(), "Now the value should be changed to true");
    }

    @Test
    void testGenericSpringEvent() {
        assertFalse(listener.isHitSuccessfulEventHandler(), "The initial value should be false");
        publisher.publishGenericEvent("Hello world!!!", true);
        assertTrue(listener.isHitSuccessfulEventHandler(), "Now the value should be changed to true");
    }

    @Test
    void testGenericSpringEventNotProcessed() {
        assertFalse(listener.isHitSuccessfulEventHandler(), "The initial value should be false");
        publisher.publishGenericEvent("Hello world!!!", false);
        assertFalse(listener.isHitSuccessfulEventHandler(), "The value should still be false");
    }

    @Disabled("fix me")
    @Test
    void testContextStartedEvent() {
        assertTrue(listener.isHitContextStartedHandler(), "Start should be called once");
    }
}