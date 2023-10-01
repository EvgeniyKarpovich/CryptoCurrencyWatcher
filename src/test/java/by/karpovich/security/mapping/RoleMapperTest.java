package by.karpovich.security.mapping;

import by.karpovich.security.api.dto.role.RoleDto;
import by.karpovich.security.api.dto.role.RoleFullDtoOut;
import by.karpovich.security.jpa.entity.RoleEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.print.attribute.standard.MediaSize;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
class RoleMapperTest {

    private static final Long ID = 1L;
    private static final String NAME = "BOSS";

    @InjectMocks
    private RoleMapper roleMapper;

    @Test
    void mapEntityFromDto() {
        RoleEntity result = roleMapper.mapEntityFromDto(generateDto());

        assertNull(result.getId());
        assertEquals(result.getName(), NAME);
    }

    @Test
    void mapRoleDtoOutFromRoleEntity() {
        RoleFullDtoOut result = roleMapper.mapRoleDtoOutFromRoleEntity(generateEntity());

        assertEquals(result.getId(), ID);
        assertEquals(result.getName(), NAME);
    }

    @Test
    void mapDtoFromEntity() {
        RoleDto result = roleMapper.mapDtoFromEntity(generateEntity());

        assertEquals(result.getName(), NAME);
    }

    @Test
    void mapListDtoFromListEntity() {
        List<RoleEntity> listEntities = Arrays.asList(generateEntity(), generateEntity(),generateEntity());
        List<RoleDto> result = roleMapper.mapListDtoFromListEntity(listEntities);

        assertThat("Wrong result size", result, hasSize(3));
        assertThat("Wrong result", result, allOf(
                hasItem(allOf(
//                        hasProperty("id", is(ID)),
                        hasProperty("name", is(NAME))
                )),
                hasItem(allOf(
//                        hasProperty("id", is(ID)),
                        hasProperty("name", is(NAME))
                )),
                hasItem(allOf(
//                        hasProperty("id", is(ID)),
                        hasProperty("name", is(NAME))
                ))
        ));
    }

    private RoleEntity generateEntity() {
        return new RoleEntity()
                .setId(ID)
                .setName(NAME);
    }

    private RoleDto generateDto() {
        return RoleDto.builder()
                .name(NAME)
                .build();
    }

    private RoleFullDtoOut generateFullDtoOut() {
        return new RoleFullDtoOut()
                .setId(ID)
                .setName(NAME);
    }
}