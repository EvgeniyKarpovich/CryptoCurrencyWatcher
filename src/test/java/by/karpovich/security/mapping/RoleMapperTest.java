package by.karpovich.security.mapping;

import by.karpovich.security.api.dto.role.RoleDto;
import by.karpovich.security.jpa.entity.RoleEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
class RoleMapperTest {

    private static final String NAME = "BOSS";

    @Test
    void mapEntityFromDto() {
    }

    @Test
    void mapRoleDtoOutFromRoleEntity() {
    }

    @Test
    void mapDtoFromEntity() {
    }

    @Test
    void mapListDtoFromListEntity() {
    }

//    private RoleEntity generateModel() {
//
//        return  RoleEntity.builder()
//                .id()
//                .name()
//                .build();
//    }
//
//    private RoleDto generateDto() {
//        return RoleDto.builder()
//                .name(NAME)
//                .build();
//    }
}