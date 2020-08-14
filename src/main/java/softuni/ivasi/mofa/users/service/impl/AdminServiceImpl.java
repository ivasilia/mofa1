package softuni.ivasi.mofa.users.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.ivasi.mofa.users.models.entities.AuthorityEntity;
import softuni.ivasi.mofa.users.models.entities.UserEntity;
import softuni.ivasi.mofa.users.repo.UserRepo;
import softuni.ivasi.mofa.users.service.AdminService;
import softuni.ivasi.mofa.users.service.UserService;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserService userService;
    private final UserRepo userRepo;


    @Autowired
    public AdminServiceImpl(UserService userService, UserRepo userRepo) {
        this.userService = userService;
        this.userRepo = userRepo;
    }

    @Override
    public void setCurator(String id) {
        UserEntity user = this.userService.findEntityById(id);

        if (!user.getAuthorities().contains("ROLE_CURATOR")) {
            AuthorityEntity curatorAuthority = new AuthorityEntity();
            curatorAuthority.setName("ROLE_CURATOR");
            curatorAuthority.setUser(user);
            user.getAuthorities().add(curatorAuthority);
            this.userService.saveEntity(user);
        } else {
            return;    // TODO Custom Role Exists Exception ??
        }
    }

    public void save(UserEntity user) {
        this.userRepo.save(user);
    }
}

