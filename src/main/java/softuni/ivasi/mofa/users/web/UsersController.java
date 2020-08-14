package softuni.ivasi.mofa.users.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import softuni.ivasi.mofa.collections.service.ItemService;
import softuni.ivasi.mofa.users.models.bindings.UserUpdateBinding;
import softuni.ivasi.mofa.users.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;
    private final ItemService itemService;

    @Autowired
    public UsersController(UserService userService, ItemService itemService) {
        this.userService = userService;
        this.itemService = itemService;
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
        if (bindingResult.hasErrors()) {
            mav.addObject("userUpdateBinding", userUpdateBinding);
        } else {
            this.userService.saveUser(userUpdateBinding);
            mav.addObject("saved", true);
        }

        mav.addObject("userUpdateBinding", userUpdateBinding);

        mav.setViewName("/users/profile");
        return mav;
    }

    @GetMapping("/private-collection/{username}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ModelAndView privateCollection(ModelAndView mav,
                                          @PathVariable("username") String username) {
        mav.addObject("allItems", this.itemService.getAllItemDtos());
        mav.addObject("collectedItems", this.userService.getAllItems(username));
        mav.addObject("username", username);
        mav.setViewName("/users/user-private-collection");
        return mav;
    }

    @PostMapping("/private-collection/{username}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ModelAndView privateCollectionAddItem(ModelAndView mav,
                                                 @PathVariable("username") String username,
                                                 @RequestParam("itemId") String itemId) {

        this.userService.addItemToUser(itemId, username);
        this.itemService.increaseRating(itemId);

        mav.addObject("allItems", this.itemService.getAllItemDtos());
        mav.addObject("collectedItems", this.userService.getAllItems(username));
        mav.addObject("username", username);
        mav.setViewName("/users/user-private-collection");
        return mav;
    }

}

