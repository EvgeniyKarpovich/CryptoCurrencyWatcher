package by.karpovich.CryptoWatcher.api.dto.user;

import by.karpovich.CryptoWatcher.api.validation.emailValidator.ValidEmail;
import by.karpovich.CryptoWatcher.api.validation.usernameValidation.ValidUsername;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserForUpdate {

    @ValidUsername
    @NotBlank(message = "Enter name")
    private String username;

    @ValidEmail
    @NotBlank(message = "Enter email")
    private String email;
}
