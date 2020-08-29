package softuni.ivasi.mofa.sales.web;

import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import softuni.ivasi.mofa.sales.models.service.TicketServiceModel;
import softuni.ivasi.mofa.sales.service.SalesService;
import softuni.ivasi.mofa.sales.service.TicketService;
import softuni.ivasi.mofa.users.models.service.UserIdServiceModel;
import softuni.ivasi.mofa.users.service.UserService;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;
import java.util.Random;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/sales")
public class SalesController {

    private final SalesService salesService;
    private final TicketService ticketService;
    private final UserService userService;
    private final Random random;

    @Autowired
    public SalesController(SalesService salesService, TicketService ticketService, UserService userService, Random random) {
        this.salesService = salesService;
        this.ticketService = ticketService;
        this.userService = userService;
        this.random = random;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public String showSalesPage(Model model, Principal principal) {

        UserIdServiceModel user = this.userService
                .getIdServiceModel(principal.getName());

        model.addAttribute("allSoldTickets",
                this.ticketService.getSoldByUserId(user.getId()));

        return "sales/sales";
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public String buyTickets(Model model,
                             @RequestParam(name = "numberOfTickets") int numberOfTickets,
                             @RequestParam(name = "ticketType") String ticketType,
                             Principal principal,
                             HttpSession httpSession) {

        UserIdServiceModel user = this.userService
                .getIdServiceModel(principal.getName());

        List<TicketServiceModel> lastSold =
                lastSold = this.ticketService.sell(ticketType, numberOfTickets, user.getId());

        httpSession.setAttribute("lastSoldTickets", lastSold);

        model.addAttribute("allSoldTickets",
                this.ticketService.getSoldByUserId(user.getId()));

        return "redirect:sales/view-bought";
    }

    @GetMapping("/view-bought")
    public String viewBought(Model model, Principal principal) {

        UserIdServiceModel user = this.userService
                .getIdServiceModel(principal.getName());

        model.addAttribute("allSoldTickets",
                this.ticketService.getSoldByUserId(user.getId()));

        return "sales/view-bought";
    }
}
