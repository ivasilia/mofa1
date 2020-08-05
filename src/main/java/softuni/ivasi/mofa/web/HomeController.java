package softuni.ivasi.mofa.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import softuni.ivasi.mofa.collections.service.DepartmentService;
import softuni.ivasi.mofa.collections.service.ItemService;
import softuni.ivasi.mofa.projects.models.service.ProjectServiceModel;
import softuni.ivasi.mofa.projects.service.ProjectService;

import java.security.Principal;

@Controller
public class HomeController {
    private final DepartmentService departmentService;
    private final ProjectService projectService;
    private final ItemService itemService;

    @Autowired
    public HomeController(DepartmentService departmentService, ProjectService projectService, ItemService itemService) {
        this.departmentService = departmentService;
        this.projectService = projectService;
        this.itemService = itemService;
    }

    @GetMapping("/")
    public String index(Model model, Principal principal) {
        model.addAttribute("collections", this.departmentService.findAll());
        return "index";
    }

    @GetMapping("/projects")
    public String projects(Model model) {
        model.addAttribute("projects", this.projectService.getAll());
        return "/projects/projects";
    }

    @GetMapping("/projects/show/{id}")
    public String showProject(Model model,
                              @PathVariable("id") String id,
                              ProjectServiceModel projectServiceModel) {
        model.addAttribute("projectServiceModel", this.projectService.getById(id));
        model.addAttribute("allProjectItems", this.itemService.getAllProjectItems(id));
        return "/projects/project-show";
    }
}
