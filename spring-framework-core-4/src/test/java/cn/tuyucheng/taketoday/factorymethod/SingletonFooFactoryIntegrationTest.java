package cn.tuyucheng.taketoday.factorymethod;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/factorymethod/static-foo-config.xml")
class SingletonFooFactoryIntegrationTest {

    @Autowired
    private Foo singleton;

    @Test
    void givenValidStaticFactoryConfig_whenCreateInstance_thenInstanceIsNotNull() {
        assertNotNull(singleton);
    }
}