package softuni.ivasi.mofa.collections.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.ivasi.mofa.collections.models.entities.Item;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepo extends JpaRepository<Item, String> {
    Item findByName(String s);
    Optional<Item> findById(String id);
    List<Item> findAllByProjectId(String projectId);
    List<Item> findAllByDepartmentId(String id);
}
