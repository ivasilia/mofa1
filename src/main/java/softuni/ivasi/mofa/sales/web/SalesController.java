package softuni.ivasi.mofa.sales.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import softuni.ivasi.mofa.sales.service.SalesService;
import softuni.ivasi.mofa.sales.service.TicketService;

@Controller
@RequestMapping("/sales")
public class SalesController {

    private final SalesService salesService;
    private final TicketService ticketService;

    @Autowired
    public SalesController(SalesService salesService, TicketService ticketService) {
        this.salesService = salesService;
        this.ticketService = ticketService;
    }

    @GetMapping
    public String sales(Model model) {
        return "sales/sales";
    }
}
