package softuni.ivasi.mofa.collections.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import softuni.ivasi.mofa.collections.models.bindings.NotesAddBinding;
import softuni.ivasi.mofa.collections.models.entities.Department;
import softuni.ivasi.mofa.collections.models.entities.Item;
import softuni.ivasi.mofa.collections.models.entities.Notes;
import softuni.ivasi.mofa.collections.models.service.ItemServiceModel;
import softuni.ivasi.mofa.collections.repo.ItemRepo;
import softuni.ivasi.mofa.projects.models.entity.Project;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ItemServiceImplTest {

    private Item testItem;
    private ItemRepo mockedItemRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Mock
    private ItemServiceImpl mockedItemService;

    @BeforeEach
    void setUp() {
        this.testItem = new Item() {{
            setId("0001");
            setName("item1");
            setImageUrl("/images/item1");
        }};
        this.mockedItemRepo = mock(ItemRepo.class);
    }

    @AfterEach
    void tearDown() {
        this.mockedItemRepo.deleteAll();
    }


    @Test
    void test_RegisterItem() {
        String[] itemInput = {"ThePainting", "Author", "ABBR", "/"};
        Department department = new Department();
        this.testItem.setDepartment(department);
        this.mockedItemService.registerItem(itemInput);

        verify(this.mockedItemService).registerItem(itemInput);
    }
//
//    @Test
//    void testRegisterItem() {
//    }

    @Test
    void test_GetById_ReturnsCorrect_ItemServiceModel() {
        this.mockedItemService.saveEntity(this.testItem);
        when(this.mockedItemService
                .getById("0001"))
                .thenReturn(
                        this.modelMapper.map(
                                this.testItem, ItemServiceModel.class));
    }

    @Test
    void test_FindByName_ReturnsCorrect_ItemServiceModel() {
        this.mockedItemService.saveEntity(this.testItem);
        when(this.mockedItemService
                .finByName("item1"))
                .thenReturn(
                        this.modelMapper.map(
                                this.testItem, ItemServiceModel.class));
    }

    @Test
    void test_GetEntityById_ReturnsCorrect_Entity() {
        this.mockedItemRepo.save(this.testItem);
        when(this.mockedItemService
                .getEntityById("0001"))
                .thenReturn(this.testItem);
    }

    @Test
    void test_GetAllItems_ReturnsCorrect_EntityList() {
        List<Item> items = List.of(
                new Item("0002", "item2", "/"),
                new Item("0003", "item3", "/"),
                new Item("0004", "item4", "/")
        );

        this.mockedItemRepo.saveAll(items);

        when(this.mockedItemService
                .getAllItems())
                .thenReturn(items);
    }

    @Test
    void test_GetAllProjectItems_ReturnsCorrect_List() {
        Project project = new Project();
        this.testItem.setProject(project);
        this.mockedItemRepo.save(this.testItem);

        when(this.mockedItemService
                .getAllProjectItems(project.getId()))
                .thenReturn(List.of(
                        this.modelMapper.map(
                                this.testItem, ItemServiceModel.class)));
    }


    // TODO Test methods returning List<DTO>

//    @Test
//    void getAllItemDtos() {
//        List<Item> items = List.of(
//                new Item("0002", "item2", "/"),
//                new Item("0003", "item3", "/"),
//                new Item("0004", "item4", "/"));
//
//        this.mockedItemRepo.saveAll(items);
//
//
//    }

    @Test
    void getAllItemsExceptDeptId() {
    }

    @Test
    void save() {
    }

    @Test
    void addDepartmentToItem() {
    }

    @Test
    void saveNotesToItem() {
        NotesAddBinding notes = this.modelMapper.map(
                new Notes(), NotesAddBinding.class);

        this.mockedItemService
                .saveNotesToItem("0001", notes);

        //TODO :
//        verify(this.mockedItemService
//                .saveNotesToItem(itemServiceModel, notes));
    }

    @Test
    void addProjectToItem() {
    }
}