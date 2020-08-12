package softuni.ivasi.mofa.projects.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import softuni.ivasi.mofa.projects.models.entity.Project;
import softuni.ivasi.mofa.projects.service.ProjectService;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {

    private Project testProject;
    private String TEST_PROJECT_ID;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProjectService projectService;


    @BeforeEach
    void setUp() {
        this.testProject = new Project();
        this.testProject.setName("Test Project");

        this.projectService.save(testProject);
        Project returnedProject = this.projectService.getEntityByName("Test Project");

        TEST_PROJECT_ID = returnedProject.getId();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test_ProjectsReturnCorrectStatusCode() throws Exception {
        this.mockMvc.perform(get("/projects"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("projects"));
    }

    @Test
    void test_ShowProject_ReturnsCorrectStatusAndAttributes() throws Exception {
        this.mockMvc.perform(get("/projects/show/" + TEST_PROJECT_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("projectServiceModel"))
                .andExpect(model().attributeExists("allProjectItems"));
    }
}