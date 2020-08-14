package softuni.ivasi.mofa;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import softuni.ivasi.mofa.collections.service.DepartmentService;
import softuni.ivasi.mofa.collections.service.ItemService;
import softuni.ivasi.mofa.projects.service.ProjectService;
import softuni.ivasi.mofa.users.service.UserService;

@Component
public class ApplicationInit implements CommandLineRunner {
    private final DepartmentService departmentService;
    private final ItemService itemService;
    private final UserService userService;
    private ProjectService projectService;

    @Autowired
    public ApplicationInit(DepartmentService departmentService,
                           ItemService itemService,
                           UserService userService,
                           ProjectService projectService) {
        this.departmentService = departmentService;
        this.itemService = itemService;
        this.userService = userService;
        this.projectService = projectService;
    }

    @Override
    public void run(String... args) throws Exception {

        // TODO -- make asynchronous -- make single run -->>
        this.departmentService.initialize(); // Initialize Departments' data
        this.itemService.initialize(); // Insert sample Items
        this.userService.initializeUsers(); // Insert sample User and ADMIN
        this.projectService.initializeProjects(); // Insert sample Projects
    }
}
