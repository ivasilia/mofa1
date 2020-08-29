package softuni.ivasi.mofa.users.models.bindings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateBinding {

    private String id;

    @Length(min = 4, max = 45, message = "Username must contain between 4 and 45 characters")
    @Column(unique = true, nullable = false)
    private String username;

    @NonNull
    @Email
    private String email;

    @Length(min = 1, message = "Enter valid first name!")
    private String firstName;

    @Length(min = 1, message = "Enter valid last name!")
    private String lastName;

    @Column(columnDefinition = "VARCHAR(255) DEFAULT NULL")
    private String Address;

    @Column(columnDefinition = "VARCHAR(45) DEFAULT NULL")
    private String phoneNr;
    private LocalDateTime registeredOn;
    private LocalDateTime lastVisit;
    private boolean active;
}

