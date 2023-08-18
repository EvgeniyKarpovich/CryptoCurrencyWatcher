package by.karpovich.security.mapping;

import by.karpovich.security.api.dto.role.RoleDto;
import by.karpovich.security.api.dto.role.RoleFullDtoOut;
import by.karpovich.security.jpa.entity.RoleEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleMapper {

    public RoleEntity mapEntityFromDto(RoleDto dto) {
        if (dto == null) {
            return null;
        }

        return RoleEntity.builder()
                .name(dto.getName())
                .build();
    }

    public RoleFullDtoOut mapRoleDtoOutFromRoleEntity(RoleEntity entity) {
        if (entity == null) {
            return null;
        }

        return RoleFullDtoOut.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public RoleDto mapDtoFromEntity(RoleEntity entity) {
        if (entity == null) {
            return null;
        }

        return RoleDto.builder()
                .name(entity.getName())
                .build();
    }

    public List<RoleDto> mapListDtoFromListEntity(List<RoleEntity> entities) {
        if (entities == null) {
            return null;
        }

        List<RoleDto> dtos = new ArrayList<>();

        for (RoleEntity entity : entities) {
            dtos.add(mapDtoFromEntity(entity));
        }

        return dtos;
    }
}
