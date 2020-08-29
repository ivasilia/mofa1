package softuni.ivasi.mofa.sales.service;

import softuni.ivasi.mofa.sales.models.service.TicketServiceModel;

import java.math.BigDecimal;
import java.util.List;

public interface TicketService {

    void initializeTickets();

    List<TicketServiceModel> sell(String ticketType, int number, String userId);

    List<TicketServiceModel> getSoldByUserId(String id);

    BigDecimal getSoldPerProject();

}
