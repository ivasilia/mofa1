package softuni.ivasi.mofa.users.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import softuni.ivasi.mofa.collections.models.entities.Item;
import softuni.ivasi.mofa.users.models.bindings.UserRegisterBinding;
import softuni.ivasi.mofa.users.models.bindings.UserUpdateBinding;
import softuni.ivasi.mofa.users.models.entities.UserEntity;
import softuni.ivasi.mofa.users.models.service.UserServiceModel;
import softuni.ivasi.mofa.users.repo.UserRepo;
import softuni.ivasi.mofa.users.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {
    private UserEntity testUser;
    private UserUpdateBinding userUpdateBinding;
    private UserRepo mockedUserRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Mock
    private UserServiceImpl mockedUserService;

    @BeforeEach
    void setUp() {
        this.testUser = new UserEntity() {{
            setId("0001");
            setUsername("Hammurapi");
        }};
        this.mockedUserRepo = mock(UserRepo.class);
        this.mockedUserService.saveEntity(this.testUser);
    }

    @AfterEach
    void tearDown() {
        this.mockedUserRepo.deleteAll();
    }

    @Test
    void findByUsername_ReturnsCorrectModel() {
        this.mockedUserRepo.save(this.testUser);
        when(this.mockedUserService
                .findByUsername("Hammurapi"))
                .thenReturn(
                        this.modelMapper.map(this.testUser, UserServiceModel.class));

    }

    @Test
    void findById_ReturnsCorrectModel() {
        this.mockedUserRepo.save(this.testUser);
        when(this.mockedUserService
                .findById("0001"))
                .thenReturn(
                        this.modelMapper.map(this.testUser, UserServiceModel.class));
    }


    @Test
    void findEntityById_ReturnsCorrectEntity() {
        this.mockedUserRepo.save(this.testUser);
        when(this.mockedUserService
                .findEntityById("0001"))
                .thenReturn(this.testUser);
    }


    // TODO  solve modelmapper problem with UserUpdateBinding -->>
//    @Test
//    void findBindingById() {
//        this.mockedUserRepo.save(this.testUser);
//        when(this.mockedUserService
//                .findBindingById("0001"))
//                .thenReturn(
//                        this.modelMapper.map(this.testUser, UserUpdateBinding.class));
//    }
//
//    @Test
//    void findBindingByUsername() {
//    }

    @Test
    void register_IsExecuted() {
        UserRegisterBinding userRegisterBinding = new UserRegisterBinding();
        userRegisterBinding.setUsername("Sasho");
        this.mockedUserService.register(userRegisterBinding);
        UserEntity sasho = this.mockedUserRepo.findByUsername("Sasho").orElse(null);

        verify(this.mockedUserService, times(1))
                .register(userRegisterBinding);
        when(this.mockedUserService.findEntityById("0001")).thenReturn(sasho);   // ?????
    }


    @Test
    void save() {
        UserRegisterBinding userRegisterBinding = new UserRegisterBinding();
        userRegisterBinding.setUsername("Sasho");
        this.mockedUserService.save(userRegisterBinding);

        verify(this.mockedUserService, times(1))
                .save(userRegisterBinding);
    }


    @Test
    void countAll() {
        this.mockedUserRepo.saveAll(List.of(
                new UserEntity("Don", "1234", "don@don.com"),
                new UserEntity("Ron", "1234", "ron@ron.com"),
                new UserEntity("Toon", "1234", "toon@koopman.com")
        ));

        when(this.mockedUserService.countAll())
                .thenReturn(3);
    }

    @Test
    void countActive() {
        UserEntity don = new UserEntity("Don", "1234", "don@don.com");
        UserEntity ron = new UserEntity("Ron", "1234", "ron@ron.com");
        UserEntity oberon = new UserEntity("Toon", "1234", "toon@koopman.com");

        don.setActive(false);

        this.mockedUserRepo.saveAll(List.of(don, ron, oberon));

        when(this.mockedUserService.countActive())
                .thenReturn(2);
    }


    @Test
    void countAllWithItems() {
        UserEntity don = new UserEntity("Don", "1234", "don@don.com");
        UserEntity ron = new UserEntity("Ron", "1234", "ron@ron.com");
        UserEntity oberon = new UserEntity("Toon", "1234", "toon@koopman.com");

        don.getItems().add(new Item("NewItem"));

        this.mockedUserRepo.saveAll(List.of(don, ron, oberon));

        when(this.mockedUserService.countAllWithItems())
                .thenReturn(1);
    }

    @Test
    void setCurator() {
        this.mockedUserService.setCurator("Hammurapi");

        verify(this.mockedUserService, times(1))
                .setCurator(anyString());
    }


    @Test
    void setInactive() {
        this.mockedUserService.setInactive("Hammurapi");

        verify(this.mockedUserService, times(1))
                .setInactive(anyString());
    }


    @Test
    void findAll() {
        UserEntity don = new UserEntity("Don", "1234", "don@don.com");
        UserEntity ron = new UserEntity("Ron", "1234", "ron@ron.com");
        UserEntity oberon = new UserEntity("Toon", "1234", "toon@koopman.com");

        this.mockedUserRepo.saveAll(List.of(don, ron, oberon));

        when(this.mockedUserService.findAll())
                .thenReturn(List.of(
                        don, ron, oberon
                        ).stream()
                        .map(u -> this.modelMapper.map(
                                u, UserServiceModel.class))
                        .collect(Collectors.toList()));
    }

    @Test
    void setActive() {
        this.userUpdateBinding.setUsername("VeryActiveUser");
        this.mockedUserService.setActive(this.userUpdateBinding);
        verify(this.mockedUserService, times(1))
                .setActive(this.userUpdateBinding);
    }


    @Test
    void saveEntity() {
        this.mockedUserService.saveEntity(this.testUser);
        verify(this.mockedUserService, times(2)).saveEntity(testUser);
    }

    @Test
    void addItemToUser() {

        this.testUser.getItems().add(new Item("NewItem"));
        this.mockedUserRepo.save(this.testUser);
        this.mockedUserService.addItemToUser("888", "Hammurapi");

        verify(this.mockedUserService)
                .addItemToUser(anyString(), anyString());
        when(this.mockedUserService.findEntityById("0001"))
                .thenReturn(this.testUser);
    }

    @Test
    void isCurator() {
    }

    @Test
    void saveUser() {
    }
}