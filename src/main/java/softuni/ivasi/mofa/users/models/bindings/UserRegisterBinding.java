package softuni.ivasi.mofa.users.models.bindings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import softuni.ivasi.mofa.common.validation.FieldMatch;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldMatch(first = "password",
        second = "confirmPassword",
        message = "The passwords do not match!")
public class UserRegisterBinding {
    @NonNull
    @Length(min = 4, max = 45, message = "Username must contain between 4 and 45 characters")
    @Column(unique = true, nullable = false)
    private String username;

    @NonNull
    @Email
    private String email;

    @NotNull
    @Length(min = 1, message = "Enter valid first name!")
    private String firstName;

    @NotNull
    @Length(min = 1, message = "Enter valid last name!")
    private String lastName;

    @NonNull
    @NotNull
    @Length(min = 4, max = 45, message = "Password must contain between 4 and 45 characters")
    private String password;

    private String confirmPassword;
}

