package softuni.ivasi.mofa.users.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import softuni.ivasi.mofa.collections.models.bindings.ItemAddBinding;
import softuni.ivasi.mofa.collections.models.bindings.ItemAddCloudBinding;
import softuni.ivasi.mofa.collections.service.ItemService;
import softuni.ivasi.mofa.common.statistics.PathService;
import softuni.ivasi.mofa.common.statistics.StatsService;
import softuni.ivasi.mofa.users.models.bindings.UserUpdateBinding;
import softuni.ivasi.mofa.users.service.AdminService;
import softuni.ivasi.mofa.users.service.UserService;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private final UserService userService;
    private final ItemService itemService;
    private final StatsService statsService;
    private final PathService pathService;

    @Autowired
    public AdminController(AdminService adminService, UserService userService, ItemService itemService, StatsService statsService, PathService pathService) {
        this.adminService = adminService;
        this.userService = userService;
        this.itemService = itemService;
        this.statsService = statsService;
        this.pathService = pathService;
    }

    @GetMapping
    public String admin(Model model) {
        model.addAttribute("requestCount", statsService.getRequestCount());
        model.addAttribute("startedOn", statsService.getStartedOn());
        model.addAttribute("usersCount", userService.countAll());
        model.addAttribute("activeUsers", userService.countActive());
        model.addAttribute("usersWithItems", userService.countAllWithItems());
        model.addAttribute("requestsToProjectsCount", pathService.getCountRequestsToProjects());
        model.addAttribute("requestsToItemsCount", pathService.getCountRequestsToItems());
        return "/admin/admin";
    }

    // -- USER MANAGEMENT --  //

    @GetMapping("/users")
    public String adminUsers(Model model) {
        model.addAttribute("userServiceModels", this.userService.findAll());
        return "/admin/admin-users";
    }

    @GetMapping("/users/update/{id}")
    public ModelAndView setActiveUser(ModelAndView mav, @PathVariable("id") String id) {
        mav.addObject("userUpdateBinding", this.userService.findBindingById(id));
        mav.setViewName("/admin/update-user");
        return mav;
    }

    @PostMapping("/users/update/{id}")
    public ModelAndView setActiveUserConfirm(ModelAndView mav,
                                             @PathVariable("id") String id,
                                             @Valid UserUpdateBinding userUpdateBinding,
                                             BindingResult bindingResult) {
        mav.addObject("userUpdateBinding", userUpdateBinding);
        mav.addObject("userServiceModels", this.userService.findAll());
        mav.setViewName("redirect:/admin/users");
        this.userService.setActive(userUpdateBinding);

        return mav;
    }

    // -- END OF USERS MANAGEMENT -- //


    //  -- CURATOR MANAGEMENT --  //

    @GetMapping("/users/curator/")
    public String getCurator(@RequestParam("id") String id, Model model) {
        model.addAttribute("entity", this.userService.findById(id));
        return "/admin/set-curator";
    }

    @PostMapping("/users/curator")
    public ModelAndView setCurator(@RequestParam(value = "id") String id, ModelAndView mav) {
        if (!this.userService.isCurator(id)) {
            this.adminService.setCurator(id);
        }
        mav.setViewName("redirect:/admin/users");
        return mav;
    }

    //  -- END OF CURATOR MANAGEMENT --  //


    //  -- ITEMS MANAGEMENT --  //

    @GetMapping("/items")
    public String showItems(Model model) {
        model.addAttribute("allItems", this.itemService.getAllItemDtos());
        return "/admin/admin-items";
    }

    @GetMapping("/items/add")
    public String addItems(Model model) {
        if (!model.containsAttribute("itemAddCloudBinding")) {
            model.addAttribute("itemAddCloudBinding", new ItemAddBinding());
        }
        return "/admin/add-item";
    }

    @PostMapping("/items/add")
    public ModelAndView addItemConfirm(ModelAndView mav,
                                       @Valid ItemAddCloudBinding itemAddCloudBinding,
                                       BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            mav.addObject("itemAddCloudBinding", itemAddCloudBinding);
            mav.setViewName("/admin/add-item");
        } else {
            this.itemService.addItem(itemAddCloudBinding);
            mav.setViewName("redirect:/admin/items");
        }
        return mav;
    }
}
