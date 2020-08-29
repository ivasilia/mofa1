package softuni.ivasi.mofa.sales.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.ivasi.mofa.sales.models.entity.Ticket;
import softuni.ivasi.mofa.sales.models.entity.TicketType;
import softuni.ivasi.mofa.users.models.entities.UserEntity;

import java.util.List;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, String> {

    Ticket findFirstByTicketTypeAndSold(TicketType ticketType, boolean isSold);

    List<Ticket> findAllByUser(UserEntity user);
}
