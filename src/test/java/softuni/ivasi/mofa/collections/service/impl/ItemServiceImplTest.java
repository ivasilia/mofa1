package softuni.ivasi.mofa.collections.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import softuni.ivasi.mofa.collections.models.bindings.ItemAddBinding;
import softuni.ivasi.mofa.collections.models.bindings.NotesAddBinding;
import softuni.ivasi.mofa.collections.models.entities.Department;
import softuni.ivasi.mofa.collections.models.entities.Item;
import softuni.ivasi.mofa.collections.models.entities.Notes;
import softuni.ivasi.mofa.collections.models.service.ItemServiceModel;
import softuni.ivasi.mofa.collections.repo.ItemRepo;
import softuni.ivasi.mofa.projects.models.entity.Project;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ItemServiceImplTest {

    private Item testItem;
    private Item testItem2;
    private ItemServiceModel testItemModel;

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
            setDepartment(new Department("111", "BBB", "B"));
        }};

        this.testItem2 = new Item() {{
            setId("0002");
            setName("item1");
            setImageUrl("/images/item1");
            setDepartment(new Department("222", "CCC", "C"));
        }};

        this.testItemModel = new ItemServiceModel() {{
            setId("0002");
            setName("item2");
            setImageUrl("/images/item2");
        }};

        this.mockedItemRepo = mock(ItemRepo.class);
        this.mockedItemService.saveEntity(testItem);
        this.mockedItemService.saveEntity(testItem2);
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


    @Test
    void testRegisterItem() {
        ItemAddBinding itemAddBinding = new ItemAddBinding();
        itemAddBinding.setName("NewItem");
        this.mockedItemService.registerItem(itemAddBinding);

        verify(this.mockedItemService, times(1))
                .registerItem(itemAddBinding);
        when(this.mockedItemRepo.findByName("NewItem"))
                .thenReturn(this.modelMapper.map(itemAddBinding, Item.class));
    }


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


    @Test
    void getAllItemDtos_ReturnsCorrectList() {
        List<Item> items = List.of(
                new Item("0002", "item2", "/"),
                new Item("0003", "item3", "/"),
                new Item("0004", "item4", "/"));

        this.mockedItemRepo.saveAll(items);

        when(this.mockedItemService.getAllItemDtos())
                .thenReturn(items
                        .stream()
                        .map(i -> this.modelMapper.map(i, ItemServiceModel.class))
                        .collect(Collectors.toList()));

    }


    @Test
    void getAllItemsExceptDeptId() {

        when(this.mockedItemService.getAllItemsExceptDeptId("0002"))
                .thenReturn(List.of(
                        this.modelMapper.map(this.testItem, ItemServiceModel.class)));
    }


    @Test
    void save_VerifyNumberOfCalls() {
        this.mockedItemService.save(this.testItemModel);
        verify(this.mockedItemService).save(any());
        verify(this.mockedItemService, times(1)).save(any(ItemServiceModel.class));
    }

    @Test
    void saveEntity_VerifyCalls_AndReturnCorrectEntity() {
        this.mockedItemService.saveEntity(this.testItem);
        verify(this.mockedItemService).saveEntity(any());
        verify(this.mockedItemService, times(1)).saveEntity(any(Item.class));
        when(this.mockedItemService.getEntityById("0001")).thenReturn(this.testItem);
    }

    @Test
    void addDepartmentToItem() {
        this.mockedItemService.addDepartmentToItem(
                this.testItem.getName(), this.testItem2.getDepartment().getId());

        verify(this.mockedItemService).addDepartmentToItem(anyString(), anyString());
        verify(this.mockedItemService, times(1))
                .addDepartmentToItem(anyString(), anyString());
        when(this.mockedItemService.getEntityById("0001").getDepartment())
                .thenReturn(this.mockedItemService.getEntityById("0002").getDepartment());
    }

    @Test
    void saveNotesToItem() {
        NotesAddBinding notes = this.modelMapper.map(
                new Notes(), NotesAddBinding.class);

        this.mockedItemService
                .saveNotesToItem("0001", notes);

        verify(this.mockedItemService, times(1))
                .saveNotesToItem(anyString(), any(NotesAddBinding.class));
    }

//    @Test
//    void addProjectToItem() {
//
//    }
}