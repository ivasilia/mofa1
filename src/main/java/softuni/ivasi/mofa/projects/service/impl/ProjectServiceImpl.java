package softuni.ivasi.mofa.projects.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import softuni.ivasi.mofa.collections.models.entities.Item;
import softuni.ivasi.mofa.collections.service.ItemService;
import softuni.ivasi.mofa.projects.models.entity.Project;
import softuni.ivasi.mofa.projects.models.entity.Venue;
import softuni.ivasi.mofa.projects.models.service.ProjectServiceModel;
import softuni.ivasi.mofa.projects.repo.ProjectRepo;
import softuni.ivasi.mofa.projects.repo.VenuesRepo;
import softuni.ivasi.mofa.projects.service.ProjectService;

import javax.persistence.EntityNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepo projectRepo;
    private final VenuesRepo venuesRepo;
//    private final ItemService itemService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProjectServiceImpl(ProjectRepo projectRepo, VenuesRepo venuesRepo, ModelMapper modelMapper) {
        this.projectRepo = projectRepo;
        this.venuesRepo = venuesRepo;
//        this.itemService = itemService;
        this.modelMapper = modelMapper;
    }




    @Override
    @Async
    public void initializeProjects() throws IOException {
        if (this.projectRepo.findAll().size() == 0) {
            InputStream resource = new ClassPathResource(
                    "files/projects.txt")
                    .getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource));
            String line = reader.readLine();

            while (line != null) {
                String[] projectInput = line.split(": ");

                Project project = new Project(projectInput[0], projectInput[1], projectInput[2], projectInput[3]);
                this.projectRepo.save(project);

                line = reader.readLine();
            }
        }
    }

    @Override
    public Project getEntityById(String id) {
        return this.projectRepo.findById(id).orElse(null);
    }


    @Override
    public ProjectServiceModel getById(String id) {
        return this.modelMapper.map(this.projectRepo.findById(id).orElseThrow(() ->
                        new EntityNotFoundException("Entity with such ID does not exist!")),
                ProjectServiceModel.class);
    }

    @Override
    @Cacheable("allProjects")
    public List<ProjectServiceModel> getAll() {
        return this.projectRepo.findAll().stream()
                .map(p -> this.modelMapper.map(
                        p, ProjectServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void save(Project project) {
        this.projectRepo.save(project);
    }

    @Override
    public Project getEntityByName(String name) {
        return this.projectRepo.findByName(name);
    }


//    @Override
//    public BigDecimal calculatePrice(String venueId, String itemId, String projectId) {
//        BigDecimal price = new BigDecimal("0.0");
//        Venue venue = this.venuesRepo.findById(venueId).orElseThrow(() ->
//                new EntityNotFoundException());
//        BigDecimal insurance = this.itemService.getEntityById(itemId)
//                .getInsurancePrice();
//
//        return insurance.add(price);
//    }

}

