package softuni.ivasi.mofa.users.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import softuni.ivasi.mofa.users.models.entities.UserEntity;
import softuni.ivasi.mofa.users.models.service.UserServiceModel;
import softuni.ivasi.mofa.users.repo.UserRepo;
import softuni.ivasi.mofa.users.service.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {
    private UserEntity testUser;
    private UserRepo mockedUserRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Mock
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        this.testUser = new UserEntity() {{
            setId("0001");
            setUsername("Hammurapi");
        }};
        this.mockedUserRepo = mock(UserRepo.class);
    }

    @AfterEach
    void tearDown() {
        this.mockedUserRepo.deleteAll();
    }

    @Test
    void findByUsername() {
        this.mockedUserRepo.save(this.testUser);
//        when(this.userService
//                .findByUsername("Hammurapi"))
//                .thenReturn(
//                        this.modelMapper.map(this.testUser, UserServiceModel.class));
        assertEquals(1, this.mockedUserRepo.count());
    }

    @Test
    void findById() {
    }

    @Test
    void findEntityById() {
    }

    @Test
    void findBindingById() {
    }

    @Test
    void findBindingByUsername() {
    }

    @Test
    void register() {
    }

    @Test
    void save() {
    }

    @Test
    void countAll() {
    }

    @Test
    void countActive() {
    }

    @Test
    void countAllWithItems() {
    }

    @Test
    void setCurator() {
    }

    @Test
    void setInactive() {
    }

    @Test
    void findAll() {
    }

    @Test
    void setActive() {
    }

    @Test
    void saveEntity() {
    }

    @Test
    void addItemToUser() {
    }

    @Test
    void isCurator() {
    }

    @Test
    void saveUser() {
    }
}