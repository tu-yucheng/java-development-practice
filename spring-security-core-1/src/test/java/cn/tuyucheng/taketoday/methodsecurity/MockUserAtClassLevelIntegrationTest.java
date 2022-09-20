package cn.tuyucheng.taketoday.methodsecurity;

import cn.tuyucheng.taketoday.methodsecurity.service.UserRoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@WithMockUser(username = "john", roles = {"VIEWER"})
class MockUserAtClassLevelIntegrationTest {

    @Autowired
    UserRoleService userService;

    @Test
    void givenRoleViewer_whenCallGetUsername_thenReturnUsername() {
        String currentUserName = userService.getUsername();
        assertEquals("john", currentUserName);
    }

    @Configuration
    @ComponentScan("cn.tuyucheng.taketoday.methodsecurity.*")
    public static class SpringConfig {

    }
}