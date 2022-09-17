package cn.tuyucheng.taketoday.factorymethod;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/factorymethod/instance-foo-config.xml")
class InstanceFooFactoryIntegrationTest {

    @Autowired
    private Foo foo;

    @Test
    void givenValidInstanceFactoryConfig_whenCreateFooInstance_thenInstanceIsNotNull() {
        assertNotNull(foo);
    }
}