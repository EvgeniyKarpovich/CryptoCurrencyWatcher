package by.karpovich.security.api.validation.emailValidator;

import by.karpovich.security.jpa.entity.UserEntity;
import by.karpovich.security.jpa.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email == null) {
            return false;
        }
        Optional<UserEntity> entity = userRepository.findByEmail(email);
        return !entity.isPresent();
    }
}
