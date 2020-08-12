package softuni.ivasi.mofa.projects.service;

import softuni.ivasi.mofa.projects.models.entity.Project;
import softuni.ivasi.mofa.projects.models.entity.Venue;
import softuni.ivasi.mofa.projects.models.service.ProjectServiceModel;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ProjectService {

    void initializeProjects() throws IOException;

    Project getEntityById(String id);

    ProjectServiceModel getById(String id);

    List<ProjectServiceModel> getAll();

    void save(Project project);

    Project getEntityByName(String name);

//    BigDecimal calculatePrice(String venueId, String itemId, String projectId);
}
