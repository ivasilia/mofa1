package softuni.ivasi.mofa.collections.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.ivasi.mofa.collections.models.entities.Department;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, String> {
    Optional<Department> findByAbbreviation(String abbreviation);

    List<Department> findByIdIsNot(String id);
}
