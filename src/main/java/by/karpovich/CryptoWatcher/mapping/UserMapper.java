package by.karpovich.CryptoWatcher.mapping;

import by.karpovich.CryptoWatcher.api.dto.authentification.RegistrationForm;
import by.karpovich.CryptoWatcher.jpa.entity.UserEntity;
import by.karpovich.CryptoWatcher.service.RoleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private static final String ROLE_USER = "ROLE_USER";
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleServiceImpl roleServiceImpl;

    public UserEntity mapEntityFromDtoForRegForm(RegistrationForm dto) {
        if (dto == null) {
            return null;
        }

        return UserEntity.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .roles(roleServiceImpl.findRoleByName(ROLE_USER))
                .build();
    }
}
