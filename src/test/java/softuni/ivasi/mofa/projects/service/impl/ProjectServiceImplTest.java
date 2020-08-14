package softuni.ivasi.mofa.projects.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import softuni.ivasi.mofa.projects.models.entity.Project;
import softuni.ivasi.mofa.projects.models.service.ProjectServiceModel;
import softuni.ivasi.mofa.projects.repo.ProjectRepo;
import softuni.ivasi.mofa.projects.service.ProjectService;
import softuni.ivasi.mofa.users.models.entities.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProjectServiceImplTest {

    private Project testProject;
    private String TEST_PROJECT_ID;

    @Autowired
    private ModelMapper modelMapper;

    @Mock
    private ProjectServiceImpl mockedProjectService;

    @Autowired
    private ProjectRepo mockedProjectRepo;

    @BeforeEach
    void setUp() {
        testProject = new Project();
        testProject.setId("0001");
        testProject.setName("MyProject");
    }

    @AfterEach
    void tearDown() {
        this.mockedProjectRepo.deleteAll();
    }

//    @Test
//    void initializeProjects() {
//    }


    @Test
    void getEntityById_ReturnsCorrectObject() {

        when(this.mockedProjectService.getEntityById("0001"))
                .thenReturn(this.testProject);
    }


    @Test
    void getById() {
        ProjectServiceModel projectServiceModel = new ProjectServiceModel();
        projectServiceModel.setName("ProjectModel");
        projectServiceModel.setId("0002");

        when(this.mockedProjectService.getById("0002"))
                .thenReturn(projectServiceModel);
    }

    @Test
    void getAll() {

        List<Project> testProjects = List.of(
                new Project("NNN"),
                new Project("MMM"),
                new Project("LLL"));

        when(this.mockedProjectService.getAll())
                .thenReturn(List.of(testProjects)
                        .stream()
                        .map(p -> this.modelMapper.map(
                                p, ProjectServiceModel.class))
                .collect(Collectors.toList()));
    }

    @Test
    void save() {
        this.mockedProjectService.save(this.testProject);
        verify(this.mockedProjectService, times(1)).save(this.testProject);
    }

    @Test
    void getEntityByName() {
        this.mockedProjectService.save(this.testProject);

        when(this.mockedProjectService.getEntityByName("MyProject"))
                .thenReturn(this.testProject);
    }
}