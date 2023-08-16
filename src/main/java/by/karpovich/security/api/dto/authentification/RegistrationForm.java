package by.karpovich.security.api.dto.authentification;

import by.karpovich.security.api.validation.emailValidator.ValidEmail;
import by.karpovich.security.api.validation.passwordValidation.PasswordMatch;
import by.karpovich.security.api.validation.usernameValidation.ValidUsername;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@PasswordMatch(
        field = "password",
        fieldMatch = "verifyPassword",
        message = "Passwords do not match!")
public class RegistrationForm {

    @ValidUsername
    @NotBlank(message = "Enter name")
    private String username;

    @ValidEmail
    @NotBlank(message = "Enter email")
    private String email;

    @NotBlank(message = "Enter password")
    private String password;

    @NotBlank(message = "Enter verifyPassword")
    private String verifyPassword;
}