package softuni.ivasi.mofa.users.service;

import softuni.ivasi.mofa.users.models.bindings.UserRegisterBinding;
import softuni.ivasi.mofa.users.models.bindings.UserUpdateBinding;
import softuni.ivasi.mofa.users.models.entities.UserEntity;
import softuni.ivasi.mofa.users.models.service.UserServiceModel;

import java.util.List;

public interface UserService {
    UserServiceModel findByUsername(String username);

    void register(UserRegisterBinding user);

    void initializeUsers();

    UserServiceModel findById(String id);

    UserEntity findEntityById(String id);

    UserUpdateBinding findBindingById(String id);

    UserUpdateBinding findBindingByUsername(String username);

    void save(UserRegisterBinding userRegisterBinding);

    int countAll();

    int countActive();

    int countAllWithItems();

    void setCurator(String username);

    void setInactive(String username);

    List<UserServiceModel> findAll();

    void setActive(UserUpdateBinding userUpdateBinding);

    void saveEntity(UserEntity user);

    boolean isCurator(String id);

    boolean saveUser(UserUpdateBinding userUpdateBinding);
}
