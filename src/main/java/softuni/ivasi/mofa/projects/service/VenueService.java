package softuni.ivasi.mofa.projects.service;

import org.springframework.stereotype.Service;
import softuni.ivasi.mofa.projects.models.entity.Venue;

@Service
public interface VenueService {
    Venue getByName(String name);
}
