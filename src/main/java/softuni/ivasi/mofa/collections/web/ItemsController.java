package softuni.ivasi.mofa.collections.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import softuni.ivasi.mofa.collections.models.bindings.NotesAddBinding;
import softuni.ivasi.mofa.collections.models.service.ItemServiceModel;
import softuni.ivasi.mofa.collections.service.ItemService;
import softuni.ivasi.mofa.collections.service.NotesService;
import softuni.ivasi.mofa.users.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/items")
public class ItemsController {

    private final ItemService itemService;
    private final UserService userService;
    private final NotesService notesService;

    @Autowired
    public ItemsController(ItemService itemService, UserService userService, NotesService notesService) {
        this.itemService = itemService;
        this.userService = userService;
        this.notesService = notesService;
    }

    @GetMapping("/show/{id}")
    public String getById(@PathVariable("id") String id, Model model) {
        model.addAttribute("item", this.itemService.getById(id));
        return "/collections/item-show";
    }

    @PreAuthorize("hasRole('ROLE_CURATOR')")
    @PostMapping("/show/{id}")
    public ModelAndView postNotes(ModelAndView mav,
                                  Principal principal,
                                  @PathVariable("id") String id,
                                  @RequestParam(value="text") String text) {

        ItemServiceModel itemServiceModel = this.itemService.getById(id);

        NotesAddBinding notes = new NotesAddBinding(text, principal.getName(), id);
//            this.notesService.save(notes);
        this.itemService.saveNotesToItem(itemServiceModel, notes);

        mav.addObject("item", itemServiceModel);
        mav.setViewName("/collections/item-show");

        return mav;
    }

}
