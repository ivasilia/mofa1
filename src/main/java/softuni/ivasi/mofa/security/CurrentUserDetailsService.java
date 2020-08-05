package softuni.ivasi.mofa.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import softuni.ivasi.mofa.users.models.entities.AuthorityEntity;
import softuni.ivasi.mofa.users.models.entities.UserEntity;
import softuni.ivasi.mofa.users.repo.UserRepo;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CurrentUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    @Autowired
    public CurrentUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<UserEntity> userOpt = userRepo.findByUsername(s);
        return userOpt.map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found!", s)));
    }

    private UserDetails map(UserEntity userEntity) {
        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getAuthorities()
                .stream()
                .map(this::mapToGrantedAuthority)
                .collect(Collectors.toList())
        );
    }

    private GrantedAuthority mapToGrantedAuthority(AuthorityEntity authorityEntity) {
        return new SimpleGrantedAuthority(authorityEntity.getName());
    }
}
