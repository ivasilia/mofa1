package softuni.ivasi.mofa.users.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.ivasi.mofa.users.models.bindings.UserRegisterBinding;
import softuni.ivasi.mofa.users.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // TODO Redirect ADMIN with AuthenticationSuccessHandler

    @GetMapping("/login")
    public ModelAndView login(ModelAndView mav, Principal principal) {
        mav.setViewName("login");
        return mav;
    }

    @PostMapping("/login")
    public String loginConfirm(Model model) {
        return "index";
    }

    @PostMapping("/login-error")
    public ModelAndView onLoginError(@ModelAttribute(UsernamePasswordAuthenticationFilter.
            SPRING_SECURITY_FORM_USERNAME_KEY) String username) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("error", "bad.credentials");
        modelAndView.addObject("username", username);
        modelAndView.setViewName("login");

        return modelAndView;
    }

    @GetMapping("/register")
    public String register(Model model) {
        if (!model.containsAttribute("userRegisterBinding")) {
            model.addAttribute("userRegisterBinding", new UserRegisterBinding());
        }
        return "register";
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(ModelAndView mav, @Valid UserRegisterBinding userRegisterBinding,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            mav.addObject("userRegisterBinding", userRegisterBinding);
            mav.setViewName("register");
        } else {
            this.userService.save(userRegisterBinding);
            mav.setViewName("login");
        }
        return mav;
    }
}
