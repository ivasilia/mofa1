package softuni.ivasi.mofa.users.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.BDDMockito.*;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import softuni.ivasi.mofa.users.models.entities.AuthorityEntity;
import softuni.ivasi.mofa.users.models.entities.UserEntity;
import softuni.ivasi.mofa.users.repo.UserRepo;
import softuni.ivasi.mofa.users.service.UserService;
import softuni.ivasi.mofa.users.service.impl.UserServiceImpl;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@WithMockUser(username="admin",roles={"USER","ADMIN"})
class AdminControllerTest {

    private String TEST_USER1_ID;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        UserEntity user1 = new UserEntity();
        user1.setUsername("Ossi");
        user1.setPassword("ossi");
        user1.setEmail("ossi@ossi.os");
        user1.setFirstName("O");
        user1.setLastName("S");

    }


    @Test
    void test_Admin_ReturnsCorrectStatusAndAttributes() throws Exception {
        this.mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("requestCount"))
                .andExpect(model().attributeExists("startedOn"))
                .andExpect(model().attributeExists("usersCount"))
                .andExpect(model().attributeExists("activeUsers"))
                .andExpect(model().attributeExists("usersWithItems"))
                .andExpect(model().attributeExists("requestsToProjectsCount"))
                .andExpect(model().attributeExists("requestsToItemsCount"));


    }

    @Test
    void test_AdminUsers_ReturnsCorrectStatusAndAttribute() throws Exception {
        this.mockMvc.perform(get("/admin/users"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userServiceModels"));
    }

//    @Test
//    void test_SetActiveUser_ReturnsCorrectStatus_AndAttribute() throws Exception {
////       TODO
//    }
//
//    @Test
//    void setActiveUserConfirm() {
//    }


//    @Test
//    void test_GetCurator_ReturnsCorrectStatus() throws Exception {
//
//        this.mockMvc.perform(get("/admin/users/curator/").param("id", "0001"))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("entity"));
//    }

//    @Test
//    void setCurator() {
//    }

    @Test
    void test_ShowItems_ReturnsCorrectStatus_AndAttribute() throws Exception {
        this.mockMvc.perform(get("/admin/items"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allItems"));
    }

    @Test
    void test_AddItems_ReturnsCorrectStatus_AndAttribute() throws Exception {
        this.mockMvc.perform(get("/admin/items/add"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("itemAddBinding"));
    }

    @Test
    void addItemConfirm() {
    }
}