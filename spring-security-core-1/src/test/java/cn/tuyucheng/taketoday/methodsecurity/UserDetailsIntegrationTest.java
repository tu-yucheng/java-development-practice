package cn.tuyucheng.taketoday.methodsecurity;

import cn.tuyucheng.taketoday.methodsecurity.entity.CustomUser;
import cn.tuyucheng.taketoday.methodsecurity.service.UserRoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
class UserDetailsIntegrationTest {

    @Autowired
    UserRoleService userService;

    @Configuration
    @ComponentScan("cn.tuyucheng.taketoday.methodsecurity.*")
    public static class SpringConfig {

    }

    @Test
    @WithUserDetails(value = "john", userDetailsServiceBeanName = "userDetailService")
    void whenJohn_callLoadUserDetail_thenOK() {
        CustomUser user = userService.loadUserDetail("jane");
        assertEquals("jane", user.getNickName());
    }

    @Test
    @WithUserDetails(value = "jane", userDetailsServiceBeanName = "userDetailService")
    void givenJane_callSecuredLoadUserDetailWithJane_thenOK() {
        CustomUser user = userService.securedLoadUserDetail("jane");
        assertEquals("jane", user.getNickName());
        assertEquals("jane", user.getUsername());
    }

    @Test
    @WithUserDetails(value = "john", userDetailsServiceBeanName = "userDetailService")
    void givenJohn_callSecuredLoadUserDetailWithJane_thenAccessDenied() {
        assertThrows(AccessDeniedException.class, () -> userService.securedLoadUserDetail("jane"));
    }

    @Test
    @WithUserDetails(value = "john", userDetailsServiceBeanName = "userDetailService")
    void givenJohn_callSecuredLoadUserDetailWithJohn_thenAccessDenied() {
        assertThrows(AccessDeniedException.class, () -> userService.securedLoadUserDetail("john"));
    }
}