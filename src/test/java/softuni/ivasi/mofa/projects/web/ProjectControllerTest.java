package softuni.ivasi.mofa.projects.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import softuni.ivasi.mofa.projects.models.entity.Project;
import softuni.ivasi.mofa.projects.repo.ProjectRepo;
import softuni.ivasi.mofa.projects.service.ProjectService;
import softuni.ivasi.mofa.projects.service.impl.ProjectServiceImpl;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ProjectControllerTest {

    private Project testProject;
    private String TEST_PROJECT_ID;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProjectRepo projectRepo;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        this.testProject = new Project("NewProject");
        this.projectRepo.save(this.testProject);
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

        this.mockMvc.perform(get("/projects/show/{id}", "0001"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("projectServiceModel"))
                .andExpect(model().attributeExists("allProjectItems"));
    }
}