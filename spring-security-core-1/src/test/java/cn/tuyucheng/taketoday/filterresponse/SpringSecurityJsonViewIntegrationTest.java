package cn.tuyucheng.taketoday.filterresponse;

import cn.tuyucheng.taketoday.filterresponse.config.AppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
class SpringSecurityJsonViewIntegrationTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    @WithMockUser(username = "admin", password = "adminPass", roles = "ADMIN")
    void whenAdminRequests_thenOwnerNameIsPresent() throws Exception {
        mvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Item 1"))
                .andExpect(jsonPath("$[0].ownerName").exists());
    }

    @Test
    @WithMockUser(username = "user", password = "userPass", roles = "USER")
    void whenUserRequests_thenOwnerNameIsAbsent() throws Exception {
        mvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Item 1"))
                .andExpect(jsonPath("$[0].ownerName").doesNotExist());
    }

    @Test
    @WithMockUser(username = "user", password = "userPass", roles = {"ADMIN", "USER"})
    void whenMultipleRoles_thenExceptionIsThrown() throws Exception {
        assertThatThrownBy(() -> mvc.perform(get("/items")).andExpect(status().isOk()))
                .hasCauseExactlyInstanceOf(IllegalArgumentException.class)
                .hasRootCauseMessage("Ambiguous @JsonView declaration for roles ROLE_ADMIN,ROLE_USER");
    }
}