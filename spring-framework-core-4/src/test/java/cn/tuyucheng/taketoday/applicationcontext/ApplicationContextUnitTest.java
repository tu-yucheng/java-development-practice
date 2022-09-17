package cn.tuyucheng.taketoday.applicationcontext;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ApplicationContextUnitTest {

    @Test
    void givenAnnotationConfigAppContext_whenSpringConfig_thenMappingSuccess() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AccountConfig.class);
        AccountService accountService = context.getBean(AccountService.class);
        assertNotNull(accountService);
        assertNotNull(accountService.getAccountRepository());
        context.close();
    }

    @Test
    void givenClasspathXmlAppContext_whenAnnotationConfig_thenMappingSuccess() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationcontext/user-bean-config.xml");
        UserService userService = context.getBean(UserService.class);
        assertNotNull(userService);
        context.close();
    }

    @Test
    void givenFileXmlAppContext_whenXMLConfig_thenMappingSuccess() {
        String path = "D:/java-workspace/intellij-workspace/java-development-practice/spring-framework-core-4/src/test/resources/applicationcontext/user-bean-config.xml";
        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(path);
        AccountService accountService = context.getBean("accountService", AccountService.class);
        assertNotNull(accountService);
        assertNotNull(accountService.getAccountRepository());
        context.close();
    }

    @Test
    void givenClasspathXmlAppContext_whenXMLConfig_thenMappingSuccess() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationcontext/account-bean-config.xml");
        AccountService accountService = context.getBean("accountService", AccountService.class);
        assertNotNull(accountService);
        assertNotNull(accountService.getAccountRepository());
        context.close();
    }

    @Test
    void givenMessagesInFile_whenMessageResourceUsed_thenReadMessage() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AccountConfig.class);
        AccountService accountService = context.getBean(AccountService.class);
        assertEquals("TestAccount", accountService.getAccountName());
        context.close();
    }
}