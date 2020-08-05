package softuni.ivasi.mofa.projects.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.ivasi.mofa.projects.models.entity.Project;

@Repository
public interface ProjectRepo extends JpaRepository<Project, String> {
    Project findByName(String s);
}
