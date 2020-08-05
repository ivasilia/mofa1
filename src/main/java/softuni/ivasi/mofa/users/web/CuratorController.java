package softuni.ivasi.mofa.users.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import softuni.ivasi.mofa.collections.service.DepartmentService;
import softuni.ivasi.mofa.collections.service.ItemService;
import softuni.ivasi.mofa.projects.models.service.ProjectServiceModel;
import softuni.ivasi.mofa.projects.service.ProjectService;
import softuni.ivasi.mofa.users.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/curator")
public class CuratorController {
    private final ItemService itemService;
    private final UserService userService;
    private final DepartmentService departmentService;
    private final ProjectService projectService;

    @Autowired
    public CuratorController(ItemService itemService, UserService userService, DepartmentService departmentService, ProjectService projectService) {
        this.itemService = itemService;
        this.userService = userService;
        this.departmentService = departmentService;

        this.projectService = projectService;
    }

    @GetMapping("/")
    public ModelAndView curator(ModelAndView mav, Principal principal) {
        mav.addObject("collections", departmentService.findAll());
        mav.setViewName("/curator/curator");
        return mav;
    }


    //  Collections (Departments) management -->

    @GetMapping("/collection/{id}")
    public String editCollection(Model model,
                                 @PathVariable("id") String id) {
        model.addAttribute("departmentServiceModel", this.departmentService.getById(id));
        model.addAttribute("allItems", this.itemService.getAllItemsExceptDeptId(id));
        return "curator/collection-edit";
    }

    @PostMapping("/collection/{id}")
    public ModelAndView editCollectionAddItem(ModelAndView mav,
                                              @PathVariable("id") String id,
                                              @RequestParam("name") String name) {

        this.itemService.addDepartmentToItem(name, id);
        mav.addObject("departmentServiceModel", this.departmentService.getById(id));
        mav.setViewName("curator/collection-edit");
        return mav;
    }

    // <-- End Of Collections (Departments) management


    // Projects management -->

    @GetMapping("/project/{id}")
    public String editProject(Model model,
                              @PathVariable("id") String id,
                              ProjectServiceModel projectServiceModel) {
        model.addAttribute("projectServiceModel", this.projectService.getById(id));
        model.addAttribute("allItems", this.itemService.getAllItemDtos());
        model.addAttribute("allProjectItems", this.itemService.getAllProjectItems(id));
        return "curator/project-edit";
    }


    @PostMapping("/project/{id}")
    public ModelAndView editProjectAddItem(ModelAndView mav,
                                           @PathVariable("id") String id,
                                           @RequestParam("name") String name) {

//        this.projectService.addItemToProject(name, id);
        mav.addObject("assigned", this.itemService.addProjectToItem(name, id));
        mav.addObject("projectServiceModel", this.projectService.getById(id));
        mav.addObject("allItems", this.itemService.getAllItems());
        mav.addObject("allProjectItems", this.itemService.getAllProjectItems(id));
        mav.setViewName("curator/project-edit");

        return mav;
    }

    // <--  End of Projects management.
}
