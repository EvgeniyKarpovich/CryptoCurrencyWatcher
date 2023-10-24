package by.karpovich.security.mapping;

import by.karpovich.security.api.dto.user.UserDtoForCreateUpdate;
import by.karpovich.security.service.RoleServiceImpl;
import by.karpovich.security.api.dto.user.UserDtoForFindAll;
import by.karpovich.security.api.dto.user.UserDtoFullOut;
import by.karpovich.security.jpa.entity.RoleEntity;
import by.karpovich.security.jpa.entity.UserEntity;
import by.karpovich.security.jpa.entity.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private static final String ROLE_USER = "ROLE_USER";
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleServiceImpl roleServiceImpl;

    public UserEntity mapEntityFromCreateUpdateDto(UserDtoForCreateUpdate dto) {
        return Optional.ofNullable(dto)
                .map(userDto -> UserEntity.builder()
                        .username(dto.getUsername())
                        .password(passwordEncoder.encode(dto.getPassword()))
                        .email(dto.getEmail())
                        .roles(roleServiceImpl.findByName(ROLE_USER))
                        .status(UserStatus.ACTIVE)
                        .build())
                .orElse(null);
    }

    public UserDtoFullOut mapUserFullDtoFromEntity(UserEntity entity) {
        return Optional.ofNullable(entity)
                .map(dto -> UserDtoFullOut.builder()
                        .username(entity.getUsername())
                        .email(entity.getEmail())
                        .roles(getRolesFromUser(entity))
                        .userStatus(entity.getStatus().toString())
                        .build())
                .orElse(null);
    }

    public UserDtoForFindAll mapUserDtoForFindAllFromEntity(UserEntity entity) {
        return Optional.ofNullable(entity)
                .map(dto -> UserDtoForFindAll.builder()
                        .id(entity.getId())
                        .username(entity.getUsername())
                        .email(entity.getEmail())
                        .roles(getRolesFromUser(entity))
                        .userStatus(entity.getStatus().toString())
                        .build())
                .orElse(null);
    }

    public List<UserDtoForFindAll> mapListUserDtoForFindAllFromListEntity(List<UserEntity> entities) {
        return Optional.ofNullable(entities)
                .map(dtos -> entities.stream()
                        .map(this::mapUserDtoForFindAllFromEntity)
                        .collect(Collectors.toList())
                )
                .orElse(null);
    }

    private Set<String> getRolesFromUser(UserEntity entity) {
        return entity.getRoles().stream()
                .map(RoleEntity::getName)
                .collect(Collectors.toSet());
    }
}
