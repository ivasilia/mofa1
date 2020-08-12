package softuni.ivasi.mofa.web;

import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import softuni.ivasi.mofa.collections.models.entities.Department;
import softuni.ivasi.mofa.collections.models.entities.Item;
import softuni.ivasi.mofa.collections.repo.DepartmentRepo;
import softuni.ivasi.mofa.collections.repo.ItemRepo;
import softuni.ivasi.mofa.collections.service.DepartmentService;
import softuni.ivasi.mofa.collections.service.ItemService;
import softuni.ivasi.mofa.projects.service.ProjectService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testIndexReturnsCorrectStatusCode() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
        .andExpect(view().name("index"))
        .andExpect(model().attributeExists("collections"));
    }
}