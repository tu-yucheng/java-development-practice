package cn.tuyucheng.taketoday.app.test;

import cn.tuyucheng.taketoday.app.SecurityApplication;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SecurityApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class LiveTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).dispatchOptions(true).build();
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void givenUserIsManager_whenGetTasks_thenAllTasks() throws Exception {
        String allTasks = """
                [
                    {
                        "id": 1,
                        "description": "Send a fax",
                        "assignee": "pam"
                    },
                    {
                        "id": 2,
                        "description": "Print a document",
                        "assignee": "pam"
                    },
                    {
                        "id": 3,
                        "description": "Answer the phone",
                        "assignee": "pam"
                    },
                    {
                        "id": 4,
                        "description": "Call a client",
                        "assignee": "jim"
                    },
                    {
                        "id": 5,
                        "description": "Organize a meeting",
                        "assignee": "michael"
                    }
                ]""";

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().json(allTasks));
    }

    @Test
    @WithMockUser(username = "jim")
    void givenUserNotManager_whenGetTasks_thenReturnAssignedToMe() throws Exception {
        String myTasks = """
                [
                    {
                        "id": 4,
                        "description": "Call a client",
                        "assignee": "jim"
                    }
                ]""";

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().json(myTasks));
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void givenUserIsManager_whenPostTasks_thenIncludeAllTasks() throws Exception {
        String newTasks = """
                [
                    {
                        "id": 6,
                        "description": "New to Michael",
                        "assignee": "michael"
                    },
                    {
                        "id": 7,
                        "description": "New to Pam",
                        "assignee": "pam"
                    }
                ]""";

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newTasks)).andExpect(status().isOk())
                .andExpect(content().json(newTasks));
    }

    @Test
    @WithMockUser(username = "jim")
    void givenUserNotManager_whenPostTasks_thenIncludeOnlyAssignedToMe() throws Exception {
        String newTasks = """
                [
                    {
                        "description": "New to Jim",
                        "assignee": "jim"
                    },
                    {
                        "description": "New to Pam",
                        "assignee": "pam"
                    }
                ]""";

        String savedTasks = """
                [
                    {
                        "id": 6,
                        "description": "New to Jim",
                        "assignee": "jim"
                    }
                ]""";

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newTasks))
                .andExpect(status().isOk())
                .andExpect(content().json(savedTasks));
    }
}