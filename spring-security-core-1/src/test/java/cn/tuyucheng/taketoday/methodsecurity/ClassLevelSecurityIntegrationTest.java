package cn.tuyucheng.taketoday.methodsecurity;

import cn.tuyucheng.taketoday.methodsecurity.service.SystemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
class ClassLevelSecurityIntegrationTest {

    @Autowired
    SystemService systemService;

    @Configuration
    @ComponentScan("cn.tuyucheng.taketoday.methodsecurity.*")
    public static class SpringConfig {

    }

    @Test
    @WithMockUser(username = "john", roles = {"ADMIN"})
    void givenRoleAdmin_whenCallGetSystemYear_return2017() {
        String systemYear = systemService.getSystemYear();
        assertEquals("2017", systemYear);
    }

    @Test
    @WithMockUser(username = "john", roles = {"VIEWER"})
    void givenRoleViewer_whenCallGetSystemYear_returnAccessDenied() {
        assertThrows(AccessDeniedException.class, () -> systemService.getSystemYear());
    }

    @Test
    @WithMockUser(username = "john", roles = {"ADMIN"})
    void givenRoleAdmin_whenCallGetSystemDate_returnDate() {
        String systemYear = systemService.getSystemDate();
        assertEquals("31-12-2017", systemYear);
    }
}