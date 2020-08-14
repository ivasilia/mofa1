package softuni.ivasi.mofa.collections.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import softuni.ivasi.mofa.collections.models.entities.Item;
import softuni.ivasi.mofa.collections.repo.ItemRepo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class ItemsControllerTest {

    private Item testItem;
    private String TEST_ITEM_ID;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemRepo mockedItemRepo;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.testItem = new Item("NewItem");

//        this.mockedItemRepo = mock(ItemRepo.class);
        this.mockedItemRepo.save(testItem);
    }

    @AfterEach
    void tearDown() {
        this.mockedItemRepo.deleteAll();
    }

    @Test
    void getById_ReturnsCorrect() throws Exception {
        this.TEST_ITEM_ID = this.mockedItemRepo
                .findByName("NewItem")
                .getId();

        this.mockMvc.perform(get("/items/show/{id}", TEST_ITEM_ID))
                .andExpect(status().isOk());

    }

    @Test
    void postNotes() {
    }
}