package softuni.ivasi.mofa.users.models.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import softuni.ivasi.mofa.collections.models.entities.Item;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NonNull
    @Length(min = 4, max = 45, message = "Username must contain between 4 and 45 characters")
    @Column(unique = true, nullable = false)
    private String username;

    @NonNull
    @NotNull
    @Length(min = 4, max = 255, message = "Password must contain minimum 4 characters")
    private String password;

    @NonNull
    @Email(message = "Enter valid email address!")
    private String email;

    @Length(min = 1, message = "Enter valid first name!")
    private String firstName;

    @Length(min = 1, message = "Enter valid last name!")
    private String lastName;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<AuthorityEntity> authorities = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.REFRESH)
    private Set<Item> items = new HashSet<>();

    private LocalDateTime registeredOn = LocalDateTime.now();
    private LocalDateTime lastVisit;
    private boolean active = false;

    @Column(columnDefinition = "VARCHAR(255) DEFAULT NULL")
    private String Address;

    @Column(columnDefinition = "VARCHAR(45) DEFAULT NULL")
    private String phoneNr;
}
