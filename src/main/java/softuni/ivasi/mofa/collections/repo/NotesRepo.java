package softuni.ivasi.mofa.collections.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.ivasi.mofa.collections.models.entities.Notes;

@Repository
public interface NotesRepo extends JpaRepository<Notes, String> {
    Notes findByAuthor(String author);
}
