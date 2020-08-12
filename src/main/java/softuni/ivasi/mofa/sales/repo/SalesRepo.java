package softuni.ivasi.mofa.sales.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.ivasi.mofa.sales.models.entity.Sales;
import softuni.ivasi.mofa.sales.models.entity.TicketType;

import java.util.List;

@Repository
public interface SalesRepo extends JpaRepository<Sales, String> {
    Sales findByType(TicketType type);
}
