package softuni.ivasi.mofa.users.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import softuni.ivasi.mofa.collections.models.entities.Item;
import softuni.ivasi.mofa.users.models.bindings.UserRegisterBinding;
import softuni.ivasi.mofa.users.models.bindings.UserUpdateBinding;
import softuni.ivasi.mofa.users.models.entities.AuthorityEntity;
import softuni.ivasi.mofa.users.models.entities.UserEntity;
import softuni.ivasi.mofa.users.models.service.UserServiceModel;
import softuni.ivasi.mofa.users.repo.UserRepo;
import softuni.ivasi.mofa.users.service.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, ModelMapper modelMapper, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
        this.encoder = encoder;
    }

    @Override
    public UserServiceModel findByUsername(String username) {
        return this.modelMapper.map(this.userRepo.findByUsername(username)
                .orElse(null), UserServiceModel.class);
    }

    @Override
    public UserServiceModel findById(String id) {
        UserEntity userEntity = this.userRepo.findById(id).orElse(null);
        UserServiceModel user = this.modelMapper.map(
                userEntity, UserServiceModel.class);
        return user;
    }

    @Override
    public UserEntity findEntityById(String id) {
        return this.userRepo.findById(id).orElse(null);
        // TODO Handle Exception !!! -->
    }

    @Override
    public UserUpdateBinding findBindingById(String id) {
        return this.modelMapper.map(
                this.userRepo.findById(id).orElseThrow(() ->
                        new EntityNotFoundException("Entity not found!")),
                UserUpdateBinding.class);
    }

    @Override
    public UserUpdateBinding findBindingByUsername(String username) {
        return this.modelMapper.map(
                this.userRepo.findByUsername(username).orElseThrow(() ->
                        new EntityNotFoundException("Entity not found!")),
                UserUpdateBinding.class);
    }


    @Override
    public void register(UserRegisterBinding userServiceModel) {
        UserEntity user = this.modelMapper.map(userServiceModel, UserEntity.class);
        user.setActive(true);
        this.userRepo.save(user);
    }

    @Override
    public void initializeUsers() {
        // Set initial USER
        UserEntity user = new UserEntity();
        user.setUsername("oswald");
        user.setPassword(this.encoder.encode("oswald"));
        user.setActive(true);
        user.setEmail("ossi@ossi.os");
        user.setFirstName("Oswald");
        user.setLastName("Oost");

        AuthorityEntity authority = new AuthorityEntity();
        authority.setName("ROLE_USER");
        authority.setUser(user);

        user.setAuthorities(List.of(authority));
        this.userRepo.save(user);

        // Set ADMIN
        UserEntity admin = new UserEntity();
        admin.setUsername("admin");
        admin.setPassword(this.encoder.encode("admin"));
        admin.setActive(true);

        AuthorityEntity adminUserAuthority = new AuthorityEntity();
        adminUserAuthority.setName("ROLE_USER");
        adminUserAuthority.setUser(admin);

        AuthorityEntity adminAdminAuthority = new AuthorityEntity();
        adminAdminAuthority.setName("ROLE_ADMIN");
        adminAdminAuthority.setUser(admin);

        admin.setAuthorities(List.of(adminUserAuthority, adminAdminAuthority));
        this.userRepo.save(admin);
    }    // initial run


    @Override
    public void save(UserRegisterBinding userRegisterBinding) {
        UserEntity user = this.modelMapper.map(userRegisterBinding, UserEntity.class);
        user.setPassword(this.encoder.encode(userRegisterBinding.getPassword()));
        user.setActive(true);
        AuthorityEntity userAuthority = new AuthorityEntity();
        userAuthority.setName("ROLE_USER");
        userAuthority.setUser(user);

        user.setAuthorities(List.of(userAuthority));
        this.userRepo.save(user);
    }

    @Override
    public int countAll() {
        return this.userRepo.findAll().size();
    }

    @Override
    public int countActive() {
        return this.userRepo.findAllByActive(true).size();
    }

    @Override
    public int countAllWithItems() {
        return this.userRepo.findAllByItemsNotNull().size();
    }

    @Override
    public void setCurator(String username) {

        UserEntity curator = this.userRepo.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException("Username doesn't exist"));

        AuthorityEntity curatorAuthority = new AuthorityEntity();
        curatorAuthority.setName("ROLE_CURATOR");
        curatorAuthority.setUser(curator);

        this.userRepo.save(curator);
    }

    @Override
    public void setInactive(String username) {
        UserEntity user = this.userRepo.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException("Username doesn't exist!"));
        user.setActive(false);
        this.userRepo.saveAndFlush(user);
    }

    @Override
    public List<UserServiceModel> findAll() {
        return this.userRepo.findAll().stream()
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void setActive(UserUpdateBinding userUpdateBinding) {
        UserEntity user = this.userRepo.findById(userUpdateBinding.getId()).orElseThrow(
                () -> new EntityNotFoundException("No user with such username"));
        System.out.println();
        user.setActive(userUpdateBinding.isActive());

        this.userRepo.saveAndFlush(user);
    }

    @Override
    public void saveEntity(UserEntity user) {
        this.userRepo.saveAndFlush(user);
    }

    public void addItemToUser(Item item, String name) {
        UserEntity user = this.userRepo.findByUsername(name).orElseThrow(() ->
                new EntityNotFoundException("No user with such username"));
        user.getItems().add(item);
    }

    @Override
    public boolean isCurator(String id) {
        UserEntity user = this.userRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Entity not found"));

        for (AuthorityEntity auth: user.getAuthorities()) {
            if (auth.getName().equals("ROLE_CURATOR")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean saveUser(UserUpdateBinding userUpdateBinding) {
        boolean saved = false;
        UserEntity user = this.userRepo.findByUsername(userUpdateBinding.getUsername())
                .orElse(null);

        if (user != null) {
            saved = true;
        }
        user.setFirstName(userUpdateBinding.getFirstName());
        user.setLastName(userUpdateBinding.getLastName());
        user.setUsername(userUpdateBinding.getUsername());
        user.setEmail(userUpdateBinding.getEmail());
        user.setAddress(userUpdateBinding.getAddress());

        this.userRepo.saveAndFlush(user);
        return saved;
    }

    public void deleteAll() {
        this.userRepo.deleteAll();
    }
}
