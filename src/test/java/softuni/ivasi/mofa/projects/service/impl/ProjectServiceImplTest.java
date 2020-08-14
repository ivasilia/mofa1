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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        this.testProject = new Project();
        testProject.setName("MyProject");

        this.mockedProjectRepo = mock(ProjectRepo.class);
        this.mockedProjectRepo.save(this.testProject);
        this.TEST_PROJECT_ID = this.mockedProjectRepo
                .findByName("MyProject")
                .getId();
    }

    @AfterEach
    void tearDown() {
        this.mockedProjectRepo.deleteAll();
    }

//    @Test
//    void initializeProjects() {
//    }


    @Test
    void getEntityById() {
//        this.mockedProjectRepo.save(this.testProject);
//        when(this.mockedProjectService.getEntityById(this.TEST_PROJECT_ID))
//                .thenReturn(this.testProject);
    }


    @Test
    void getById() {
    }

    @Test
    void getAll() {

        List<Project> testProjects = List.of(
                new Project("NNN"),
                new Project("MMM"),
                new Project("LLL"));

        this.mockedProjectRepo.saveAll(testProjects);

        when(this.mockedProjectService.getAll())
                .thenReturn(List.of(testProjects)
                        .stream()
                        .map(p -> this.modelMapper.map(
                                p, ProjectServiceModel.class))
                .collect(Collectors.toList()));
    }

    @Test
    void save() {
    }

    @Test
    void getEntityByName() {
    }
}