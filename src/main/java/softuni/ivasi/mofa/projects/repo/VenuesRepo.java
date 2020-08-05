package softuni.ivasi.mofa.projects.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.ivasi.mofa.projects.models.entity.Venue;

@Repository
public interface VenuesRepo extends JpaRepository<Venue, String> {
}
