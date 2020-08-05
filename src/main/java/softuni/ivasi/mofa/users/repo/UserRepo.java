package softuni.ivasi.mofa.users.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.ivasi.mofa.users.models.entities.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByUsername(String s);
    Optional<UserEntity> findByFirstNameAndLastName(String fn, String ln);
    List<UserEntity> findAll();
    List<UserEntity> findAllByActive(boolean active);
    List<UserEntity> findAllByItemsNotNull();
}
