package cn.tuyucheng.taketoday.springevents.synchronous;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SynchronousSpringEventsConfig.class}, loader = AnnotationConfigContextLoader.class)
class ContextRefreshedListenerIntegrationTest {

    @Autowired
    private ContextRefreshedListener listener;

    @Test
    void testContextRefreshedListener() {
        System.out.println("Test context re-freshed listener.");
        assertTrue(listener.isHitContextRefreshedHandler(), "Refresh should be called once");
    }
}