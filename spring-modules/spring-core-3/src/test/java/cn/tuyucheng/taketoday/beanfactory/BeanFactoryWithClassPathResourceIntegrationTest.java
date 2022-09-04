package cn.tuyucheng.taketoday.beanfactory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BeanFactoryWithClassPathResourceIntegrationTest {

    @Test
    void createBeanFactoryAndCheckEmployeeBean() {
        Resource res = new ClassPathResource("beanfactory-example.xml");
        BeanFactory factory = new XmlBeanFactory(res);
        Employee emp = (Employee) factory.getBean("employee");

        assertTrue(factory.isSingleton("employee"));
        assertTrue(factory.getBean("employee") instanceof Employee);
        assertTrue(factory.isTypeMatch("employee", Employee.class));
        assertTrue(factory.getAliases("employee").length > 0);
    }
}