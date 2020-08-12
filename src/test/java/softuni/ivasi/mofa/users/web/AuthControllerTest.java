package softuni.ivasi.mofa.users.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test_LoginReturnsCorrectStatus() throws Exception {
        this.mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    void test_LoginConfirmReturnsCorrectStatus() throws Exception {
        this.mockMvc.perform(post("/login"))
                .andExpect(status().isOk());
    }

    @Test
    void onLoginError() throws Exception {
        this.mockMvc.perform(post("/login-error"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attributeExists("username"))
                .andExpect(view().name("login"));

    }

    @Test
    void test_RegisterReturnsCorrectStatus_AndAttributes() throws Exception {
        this.mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userRegisterBinding"));
    }

    @Test
    void registerConfirm() throws Exception {
        this.mockMvc.perform(post("/register"))
                .andExpect(status().isOk());
                // TODO
    }
}