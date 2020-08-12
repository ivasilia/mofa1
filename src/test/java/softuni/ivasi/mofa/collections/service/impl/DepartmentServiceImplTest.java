package softuni.ivasi.mofa.collections.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import softuni.ivasi.mofa.collections.models.entities.Department;
import softuni.ivasi.mofa.collections.repo.DepartmentRepo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DepartmentServiceImplTest {
    private Department testDepartment;
    private DepartmentRepo mockedDepartmentRepo;

    @BeforeEach
    public void initialize() {
        this.testDepartment = new Department() {{
                setId("0001");
                setName("dept1");
                setImageUrl("/images/dept1");
            }};
        this.mockedDepartmentRepo = Mockito.mock(DepartmentRepo.class);
    }

    @AfterEach
    void tearDown() {
        this.mockedDepartmentRepo.deleteAll();
    }

    @Mock
    DepartmentServiceImpl mockedDepartmentService;

    @Test
    public void test_GetByName_ReturnsCorrect() {
        Mockito.when(this.mockedDepartmentService
                .getByName("dept1"))
                .thenReturn(this.testDepartment);
    }


    @Test
    public void test_findAll_ReturnsCorrectSize() {

        List<Department> departments = List.of(
                new Department("0002", "dept2", "/", "2"),
                new Department("0003", "dept3", "/", "3"),
                new Department("0004", "dept4", "/", "4")
        );
        this.mockedDepartmentRepo.saveAll(departments);

        Mockito.when(this.mockedDepartmentService.findAll())
                .thenReturn(departments);
    }


    @Test
    public void test_getDepartmentById_ReturnsCorrect() {
        Department department = new Department("999", "dept2", "DFL");
        this.mockedDepartmentRepo.save(department);

        Mockito.when(this.mockedDepartmentService
                .getDepartmentById("0002"))
                .thenReturn(department);
    }


    @Test
    public void test_GetByAbbreviation_ReturnsCorrect() {
        Department department = new Department("999", "dept2", "DFL");
        this.mockedDepartmentRepo.save(department);

        Mockito.when(this.mockedDepartmentService
                .getByAbbreviation("DFL"))
                .thenReturn(department);
    }


    @Test
    public void test_FindByDeptIdIsNot_ReturnsCorrect() {
        this.mockedDepartmentRepo.saveAll(List.of(
                new Department("999", "dept2", "DFL"),
                new Department("888", "dept3", "SPA"),
                new Department("555", "dept4", "UKR")
        ));

        List<String> result = List.of("999", "888");
        Mockito.when(this.mockedDepartmentService.findByDepartmentIdIsNot("555"))
                .thenReturn(result);
    }

}