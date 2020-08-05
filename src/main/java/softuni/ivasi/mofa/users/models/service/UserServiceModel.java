package softuni.ivasi.mofa.users.models.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceModel {

    private String id;
    @Length(min = 4, max = 45, message = "Username must contain between 4 and 45 characters")
    private String username;

    @NotNull
    @Length(min = 4, max = 255, message = "Password must contain minimum 4 characters")
    private String password;

    @Email
    private String email;

    @Length(min = 1, message = "Enter valid first name!")
    private String firstName;

    @Length(min = 1, message = "Enter valid last name!")
    private String lastName;

    private LocalDateTime registeredOn = LocalDateTime.now();
    private LocalDateTime lastVisit;
    private boolean active;
    private String Address;
    private String phoneNr;
}

