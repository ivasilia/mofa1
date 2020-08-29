package softuni.ivasi.mofa.projects.service.impl;

import softuni.ivasi.mofa.projects.models.entity.Venue;
import softuni.ivasi.mofa.projects.repo.VenuesRepo;
import softuni.ivasi.mofa.projects.service.VenueService;

public class VenueServiceImpl implements VenueService {

    private final VenuesRepo venuesRepo;

    public VenueServiceImpl(VenuesRepo venuesRepo) {
        this.venuesRepo = venuesRepo;
    }

    @Override
    public Venue getByName(String name) {
        return this.venuesRepo.findByName(name);
    }
}
