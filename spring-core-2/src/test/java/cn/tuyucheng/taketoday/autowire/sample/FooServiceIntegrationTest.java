package cn.tuyucheng.taketoday.autowire.sample;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
class FooServiceIntegrationTest {

    @Autowired
    FooService fooService;

    @Test
    void whenFooFormatterType_thenReturnFoo() {
        assertEquals("foo", fooService.doStuff());
    }
}