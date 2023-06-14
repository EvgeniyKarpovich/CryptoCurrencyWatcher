package by.karpovich.CryptoWatcher.mapping;

import by.karpovich.CryptoWatcher.api.dto.authentification.RegistrationForm;
import by.karpovich.CryptoWatcher.api.dto.user.UserDtoForFindAll;
import by.karpovich.CryptoWatcher.api.dto.user.UserForUpdate;
import by.karpovich.CryptoWatcher.api.dto.user.UserFullDtoOut;
import by.karpovich.CryptoWatcher.jpa.entity.RoleEntity;
import by.karpovich.CryptoWatcher.jpa.entity.UserEntity;
import by.karpovich.CryptoWatcher.jpa.entity.UserStatus;
import by.karpovich.CryptoWatcher.service.RoleServiceImpl;
import by.karpovich.CryptoWatcher.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
                .userStatus(UserStatus.ACTIVE)
                .build();
    }

    public UserEntity mapEntityFromUpdateDto(UserForUpdate dto) {
        if (dto == null) {
            return null;
        }

        return UserEntity.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .build();
    }

    public UserFullDtoOut mapUserFullDtoFromEntity(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        return UserFullDtoOut.builder()
                .username(entity.getUsername())
                .email(entity.getEmail())
                .image(Utils.getImageAsResponseEntity(entity.getImage()))
                .roles(getRolesFromUser(entity))
                .userStatus(entity.getUserStatus().toString())
                .build();
    }

    public UserDtoForFindAll mapUserDtoForFindAllFromEntity(UserEntity user) {
        if (user == null) {
            return null;
        }

        return UserDtoForFindAll.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(getRolesFromUser(user))
                .userStatus(user.getUserStatus().toString())
                .build();
    }

    public List<UserDtoForFindAll> mapListUserDtoForFindAllFromListEntity(List<UserEntity> users) {
        if (users == null) {
            return null;
        }

        List<UserDtoForFindAll> usersDto = new ArrayList<>();

        for (UserEntity model : users) {
            usersDto.add(mapUserDtoForFindAllFromEntity(model));
        }
        return usersDto;
    }

    private Set<String> getRolesFromUser(UserEntity entity) {
        return entity.getRoles().stream()
                .map(RoleEntity::getName)
                .collect(Collectors.toSet());
    }
}
