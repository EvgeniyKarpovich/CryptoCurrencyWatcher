package by.karpovich.CryptoWatcher.api.validation.usernameValidation;

import by.karpovich.CryptoWatcher.jpa.entity.UserEntity;
import by.karpovich.CryptoWatcher.jpa.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (username == null) {
            return false;
        }
        Optional<UserEntity> entity = userRepository.findByUsername(username);
        return !entity.isPresent();
    }
}