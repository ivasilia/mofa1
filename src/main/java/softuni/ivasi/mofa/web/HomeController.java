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

    @Autowired
    public HomeController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/")
    public String index(Model model, Principal principal) {
        model.addAttribute("collections", this.departmentService.findAll());
        return "index";
    }
}
