package cn.tuyucheng.taketoday.methodsecurity;

import cn.tuyucheng.taketoday.methodsecurity.service.UserRoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
class MethodSecurityIntegrationTest {

    @Autowired
    UserRoleService userRoleService;

    @Configuration
    @ComponentScan("cn.tuyucheng.taketoday.methodsecurity.*")
    public static class SpringConfig {

    }

    @Test
    void givenNoSecurity_whenCallGetUsername_thenReturnException() {
        assertThrows(AuthenticationCredentialsNotFoundException.class, () -> userRoleService.getUsername());
    }

    @Test
    @WithMockUser(username = "john", roles = {"VIEWER"})
    void givenRoleViewer_whenCallGetUsername_thenReturnUsername() {
        String userName = userRoleService.getUsername();
        assertEquals("john", userName);
    }

    @Test
    @WithMockUser(username = "john", roles = {"VIEWER"})
    void givenUsernameJohn_whenCallIsValidUsername_thenReturnTrue() {
        boolean isValid = userRoleService.isValidUsername("john");
        assertTrue(isValid);
    }

    @Test
    @WithMockUser(username = "john", roles = {"ADMIN"})
    void givenRoleAdmin_whenCallGetUsername_thenReturnAccessDenied() {
        assertThrows(AccessDeniedException.class, () -> userRoleService.getUsername());
    }

    @Test
    @WithMockUser(username = "john", roles = {"USER"})
    void givenRoleUser_whenCallGetUsername2_thenReturnAccessDenied() {
        assertThrows(AccessDeniedException.class, () -> userRoleService.getUsername2());
    }

    @Test
    @WithMockUser(username = "john", roles = {"VIEWER", "EDITOR"})
    void givenRoleViewer_whenCallGetUsername2_thenReturnUsername() {
        String userName = userRoleService.getUsername2();
        assertEquals("john", userName);
    }

    @Test
    @WithMockUser(username = "john", roles = {"VIEWER"})
    void givenUsernameJerry_whenCallIsValidUsername2_thenReturnFalse() {
        boolean isValid = userRoleService.isValidUsername2("jerry");
        assertFalse(isValid);
    }

    @Test
    @WithMockUser(username = "JOHN", authorities = {"SYS_ADMIN"})
    void givenAuthoritySysAdmin_whenCallGetUsernameLC_thenReturnUsername() {
        String username = userRoleService.getUsernameLC();
        assertEquals("john", username);
    }

    @Test
    @WithMockUser(username = "john", roles = {"ADMIN", "USER", "VIEWER"})
    void givenUserJohn_whenCallGetMyRolesWithJohn_thenReturnRoles() {
        String roles = userRoleService.getMyRoles("john");
        assertEquals("ROLE_ADMIN,ROLE_USER,ROLE_VIEWER", roles);
    }

    @Test
    @WithMockUser(username = "john", roles = {"ADMIN", "USER", "VIEWER"})
    void givenUserJane_whenCallGetMyRoleWithJane_thenAccessDenied() {
        assertThrows(AccessDeniedException.class, () -> userRoleService.getMyRoles("jane"));
    }

    @Test
    @WithMockUser(username = "john", roles = {"ADMIN", "USER", "VIEWER"})
    void givenUserJohn_whenCallGetMyRoles2WithJohn_thenReturnRoles() {
        String roles = userRoleService.getMyRoles2("john");
        assertEquals("ROLE_ADMIN,ROLE_USER,ROLE_VIEWER", roles);
    }

    @Test
    @WithMockUser(username = "john", roles = {"ADMIN", "USER", "VIEWER"})
    void givenUserJane_whenCallGetMyRoles2WithJane_thenAccessDenied() {
        assertThrows(AccessDeniedException.class, () -> userRoleService.getMyRoles2("jane"));
    }

    @Test
    @WithAnonymousUser
    void givenAnonymousUser_whenCallGetUsername_thenAccessDenied() {
        assertThrows(AccessDeniedException.class, () -> userRoleService.getUsername());
    }

    @Test
    @WithMockJohnViewer
    void givenMockedJohnViewer_whenCallGetUsername_thenReturnUsername() {
        String userName = userRoleService.getUsername();
        assertEquals("john", userName);
    }

    @Test
    @WithMockUser(username = "jane")
    void givenListContainCurrentUsername_whenJoinUsernames_thenReturnUsernames() {
        List<String> usernames = new ArrayList<>();
        usernames.add("jane");
        usernames.add("john");
        usernames.add("jack");
        String containCurrentUser = userRoleService.joinUsernames(usernames);
        assertEquals("john;jack", containCurrentUser);
    }

    @Test
    @WithMockUser(username = "john")
    void givenListContainCurrentUsername_whenCallJoinUsernamesAndRoles_thenReturnUsernameAndRoles() {
        List<String> usernames = new ArrayList<>();
        usernames.add("jane");
        usernames.add("john");
        usernames.add("jack");

        List<String> roles = new ArrayList<>();
        roles.add("ROLE_ADMIN");
        roles.add("ROLE_TEST");

        String containCurrentUser = userRoleService.joinUsernamesAndRoles(usernames, roles);
        assertEquals("jane;jack:ROLE_ADMIN;ROLE_TEST", containCurrentUser);
    }

    @Test
    @WithMockUser(username = "john")
    void givenUserJohn_whenCallGetAllUsernamesExceptCurrent_thenReturnOtherusernames() {
        List<String> others = userRoleService.getAllUsernamesExceptCurrent();
        assertEquals(2, others.size());
        assertTrue(others.contains("jane"));
        assertTrue(others.contains("jack"));
    }

    @Test
    @WithMockUser(username = "john", roles = {"VIEWER"})
    void givenRoleViewer_whenCallGetUsername4_thenReturnUsername() {
        String userName = userRoleService.getUsername4();
        assertEquals("john", userName);
    }

    @Test
    @WithMockUser(username = "john")
    void givenDefaultRole_whenCallGetUsername4_thenAccessDenied() {
        assertThrows(AccessDeniedException.class, () -> userRoleService.getUsername4());
    }
}