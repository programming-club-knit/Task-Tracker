package com.hacktheweb.tasktrack;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hacktheweb.tasktrack.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanDatabase() {
        repository.deleteAll();
    }

    /** Creates a task through the API and returns its id. */
    private long createTask(String json) throws Exception {
        String body = mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        JsonNode node = objectMapper.readTree(body);
        return node.get("id").asLong();
    }

    @Test
    void createTask_returns201AndAppliesDefaults() throws Exception {
        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"  Write tests  \"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("Write tests"))
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.priority").value("MEDIUM"))
                .andExpect(jsonPath("$.createdAt").isNotEmpty());
    }

    @Test
    void getTasks_returnsAllTasks() throws Exception {
        createTask("{\"title\":\"One\"}");
        createTask("{\"title\":\"Two\",\"priority\":\"HIGH\",\"dueDate\":\"2026-10-30\"}");

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title").value("Two"))
                .andExpect(jsonPath("$[0].dueDate").value("2026-10-30"));
    }

    @Test
    void getTaskById_returnsTask() throws Exception {
        long id = createTask("{\"title\":\"Find me\"}");

        mockMvc.perform(get("/api/tasks/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Find me"));
    }

    @Test
    void updateTask_changesFields() throws Exception {
        long id = createTask("{\"title\":\"Old title\"}");

        mockMvc.perform(put("/api/tasks/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"New title\",\"description\":\"Details\","
                                + "\"status\":\"COMPLETED\",\"priority\":\"LOW\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New title"))
                .andExpect(jsonPath("$.description").value("Details"))
                .andExpect(jsonPath("$.status").value("COMPLETED"))
                .andExpect(jsonPath("$.priority").value("LOW"));
    }

    @Test
    void deleteTask_returns204ThenTaskIsGone() throws Exception {
        long id = createTask("{\"title\":\"Delete me\"}");

        mockMvc.perform(delete("/api/tasks/" + id)).andExpect(status().isNoContent());
        mockMvc.perform(get("/api/tasks/" + id)).andExpect(status().isNotFound());
    }

    @Test
    void deleteCompletedTasks_deletesCompletedTasksAndKeepsPendingTasks() throws Exception {
        long completedId = createTask("{\"title\":\"Completed\",\"status\":\"COMPLETED\"}");
        long pendingId = createTask("{\"title\":\"Pending\"}");

        mockMvc.perform(delete("/api/tasks/completed"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deleted").value(1));

        mockMvc.perform(get("/api/tasks/" + completedId)).andExpect(status().isNotFound());
        mockMvc.perform(get("/api/tasks/" + pendingId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void deleteCompletedTasks_returnsZeroWhenNoneExist() throws Exception {
        createTask("{\"title\":\"Pending\"}");

        mockMvc.perform(delete("/api/tasks/completed"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deleted").value(0));
    }

    @Test
    void missingTask_returns404WithJsonError() throws Exception {
        mockMvc.perform(get("/api/tasks/99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Task with id 99999 was not found"));

        mockMvc.perform(put("/api/tasks/99999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"x\",\"status\":\"PENDING\",\"priority\":\"LOW\"}"))
                .andExpect(status().isNotFound());

        mockMvc.perform(delete("/api/tasks/99999")).andExpect(status().isNotFound());
    }

    @Test
    void blankTitle_isRejectedWith400() throws Exception {
        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"   \"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details.title").value("Title is required"));
    }
}