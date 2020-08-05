package softuni.ivasi.mofa.sales.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.ivasi.mofa.sales.models.entity.Ticket;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, String> {
}
