package softuni.ivasi.mofa.users.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.ivasi.mofa.users.models.bindings.UserUpdateBinding;
import softuni.ivasi.mofa.users.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        model.addAttribute("userUpdateBinding", this.userService.findBindingByUsername(principal.getName()));
        return "/users/profile";
    }

    @PostMapping("/profile")
    public ModelAndView editProfile(ModelAndView mav,
                                    Principal principal,
                                    @Valid UserUpdateBinding userUpdateBinding,
                                    BindingResult bindingResult) {
        mav.addObject("userUpdateBinding", userUpdateBinding);
        mav.setViewName("/users/profile");
        mav.addObject("saved", this.userService.saveUser(userUpdateBinding));
        return mav;
    }
}

