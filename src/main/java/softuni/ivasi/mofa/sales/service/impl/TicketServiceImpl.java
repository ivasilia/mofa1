package softuni.ivasi.mofa.sales.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import softuni.ivasi.mofa.sales.models.entity.Ticket;
import softuni.ivasi.mofa.sales.models.entity.TicketType;
import softuni.ivasi.mofa.sales.models.service.TicketServiceModel;
import softuni.ivasi.mofa.sales.repo.TicketRepo;
import softuni.ivasi.mofa.sales.service.TicketService;
import softuni.ivasi.mofa.users.models.entities.UserEntity;
import softuni.ivasi.mofa.users.repo.UserRepo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepo ticketRepo;
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    public TicketServiceImpl(TicketRepo ticketRepo, UserRepo userRepo, ModelMapper modelMapper) {
        this.ticketRepo = ticketRepo;
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    @Async
    public void initializeTickets() {
        this.initializeTicketType(TicketType.ORDINARY_TICKET, 15.00);
        this.initializeTicketType(TicketType.PROJECT_TICKET, 18.00);
        this.initializeTicketType(TicketType.DISCOUNT_TICKET, 12.00);
    }

    private void initializeTicketType(TicketType ticketType, double price) {
        for (int i = 0; i < 100; i++) {
            Ticket ticket = new Ticket(
                    ticketType, new BigDecimal(price));
            this.ticketRepo.save(ticket);
        }
    }

    @Override
    public List<TicketServiceModel> sell(String ticketType,
                                         int number,
                                         String userId) {
        List<TicketServiceModel> ticketsToReturn = new ArrayList<>();
        UserEntity user = this.userRepo.findById(userId).orElse(null);

        for (int i = 0; i < number; i++) {
            Ticket ticket = this.ticketRepo.findFirstByTicketTypeAndSold(
                    TicketType.valueOf(ticketType), false);
            ticket.setSold(true);
            ticket.setUser(user);

            // TICKET SALES FUNCTION IS REALIZED THROUGH Ticket.userId (not through User)
//            user.getTickets().add(ticket);
//            this.userRepo.save(user);

            this.ticketRepo.save(ticket);
            ticketsToReturn.add(
                    this.modelMapper.map(
                            ticket, TicketServiceModel.class));
        }

        return ticketsToReturn;
    }

    @Override
    public List<TicketServiceModel> getSoldByUserId(String userId) {
        UserEntity user = this.userRepo.findById(userId).orElse(null);
        return this.ticketRepo.findAllByUser(user).stream()
                .map(ticket -> this.modelMapper.map(ticket, TicketServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal getSoldPerProject() {
        return null;
    }

}
