package softuni.ivasi.mofa.collections.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import softuni.ivasi.mofa.collections.service.DepartmentService;

import java.security.Principal;

@Controller
@RequestMapping("/collections")
public class CollectionsController {

    private final DepartmentService departmentService;

    @Autowired
    public CollectionsController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    @GetMapping("/show/{id}")
    public String showById(@PathVariable("id") String id, Model model, Principal principal) {
        model.addAttribute("collection", this.departmentService.getById(id));
        return "/collections/collections-show";
    }
}
