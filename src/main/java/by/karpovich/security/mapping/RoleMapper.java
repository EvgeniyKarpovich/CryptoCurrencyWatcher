package by.karpovich.security.mapping;

import by.karpovich.security.api.dto.role.RoleDto;
import by.karpovich.security.jpa.entity.RoleEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RoleMapper {

    public RoleEntity mapEntityFromDto(RoleDto dto) {
        return Optional.ofNullable(dto)
                .map(roleDto -> RoleEntity.builder()
                        .name(roleDto.getName())
                        .build()
                ).orElse(null);
    }

    public RoleDto mapDtoFromEntity(RoleEntity entity) {
        return Optional.ofNullable(entity)
                .map(dto -> RoleDto.builder()
                        .id(dto.getId())
                        .name(dto.getName())
                        .build()
                ).orElse(null);
    }

    public List<RoleDto> mapListDtoFromListEntity(List<RoleEntity> entities) {
        return Optional.ofNullable(entities)
                .map(list -> list.stream()
                        .map(this::mapDtoFromEntity)
                        .collect(Collectors.toList())
                )
                .orElse(null);
    }
}
