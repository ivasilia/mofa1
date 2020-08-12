package softuni.ivasi.mofa.projects.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import softuni.ivasi.mofa.collections.service.ItemService;
import softuni.ivasi.mofa.projects.models.service.ProjectServiceModel;
import softuni.ivasi.mofa.projects.service.ProjectService;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final ItemService itemService;

    @Autowired
    public ProjectController(ProjectService projectService, ItemService itemService) {
        this.projectService = projectService;
        this.itemService = itemService;
    }

    @GetMapping
    public String projects(Model model) {
        model.addAttribute("projects", this.projectService.getAll());
        return "/projects/projects";
    }

    @GetMapping("/show/{id}")
    public String showProject(Model model,
                              @PathVariable("id") String id,
                              ProjectServiceModel projectServiceModel) {
        model.addAttribute("projectServiceModel", this.projectService.getById(id));
        model.addAttribute("allProjectItems", this.itemService.getAllProjectItems(id));
        return "/projects/project-show";
    }
}
