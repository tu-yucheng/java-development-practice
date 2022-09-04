package cn.tuyucheng.taketoday.scopes;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScopesIntegrationTest {
    private static final String NAME = "John Smith";
    private static final String NAME_OTHER = "Anna Jones";

    @Test
    void givenSingletonScope_whenSetName_thenEqualNames() {
        final AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext("scopes.xml");

        final Person personSingletonA = (Person) applicationContext.getBean("personSingleton");
        final Person personSingletonB = (Person) applicationContext.getBean("personSingleton");
        personSingletonA.setName(NAME);
        assertEquals(NAME, personSingletonB.getName());

        applicationContext.close();
    }

    @Test
    void givenPrototypeScope_whenSetNames_thenDifferentNames() {
        final AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext("scopes.xml");

        final Person personPrototypeA = (Person) applicationContext.getBean("personPrototype");
        final Person personPrototypeB = (Person) applicationContext.getBean("personPrototype");

        personPrototypeA.setName(NAME);
        personPrototypeB.setName(NAME_OTHER);
        assertEquals(NAME, personPrototypeA.getName());
        assertEquals(NAME_OTHER, personPrototypeB.getName());

        applicationContext.close();
    }
}