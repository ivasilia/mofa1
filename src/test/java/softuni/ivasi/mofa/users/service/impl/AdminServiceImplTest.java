package softuni.ivasi.mofa.users.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import softuni.ivasi.mofa.users.models.entities.AuthorityEntity;
import softuni.ivasi.mofa.users.models.entities.UserEntity;
import softuni.ivasi.mofa.users.repo.UserRepo;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminServiceImplTest {

    private UserEntity testUser;
    private String TEST_USER_ID;

    @Autowired
    private UserRepo mockedUserRepo;

    @Mock
    private AdminServiceImpl mockedAdminService;

    @BeforeEach
    void setUp() {
        this.mockedUserRepo = mock(UserRepo.class);

        testUser = new UserEntity();
        testUser.setId("0001");
        testUser.setUsername("test-curator");
        testUser.setPassword("1234");
        testUser.setEmail("k@k.com");
        testUser.setFirstName("B");
        testUser.setLastName("B");

        this.mockedAdminService.save(this.testUser);

//        this.TEST_USER_ID = this.mockedUserRepo
//                .findByUsername("test-curator").orElse(null)
//                .getId();
    }


    @Test
    void setCurator() {

        this.mockedAdminService.setCurator("0001");

        verify(this.mockedAdminService, times(1))
                .setCurator("0001");
    }
}