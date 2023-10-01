package by.karpovich.security.service;

import by.karpovich.security.api.dto.role.RoleDto;
import by.karpovich.security.api.dto.role.RoleFullDtoOut;
import by.karpovich.security.exception.NotFoundModelException;
import by.karpovich.security.jpa.entity.RoleEntity;
import by.karpovich.security.jpa.repository.RoleRepository;
import by.karpovich.security.mapping.RoleMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
class RoleServiceImplTest {

    private static final Long ID = 1L;
    private static final String ROLE_NAME = "USER";

    @Mock
    private RoleRepository roleRepository;
    @Mock
    private RoleMapper roleMapper;
    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    void saveRole() {
        when(roleRepository.findById(anyLong())).thenReturn(Optional.empty());

        RoleEntity mapped = mock(RoleEntity.class);
        when(roleMapper.mapEntityFromDto(any(RoleDto.class))).thenReturn(mapped);

        RoleEntity saved = mock(RoleEntity.class);
        when(roleRepository.save(any(RoleEntity.class))).thenReturn(saved);

        RoleFullDtoOut returned = mock(RoleFullDtoOut.class);
        when(roleMapper.mapRoleDtoOutFromRoleEntity(any(RoleEntity.class))).thenReturn(returned);

        RoleDto dto = new RoleDto();

        RoleFullDtoOut result = roleService.saveRole(dto);
        assertEquals(result, returned);

        verify(roleRepository).findByName(dto.getName());
        verify(roleMapper).mapEntityFromDto(dto);
        verify(roleRepository).save(mapped);
        verify(roleMapper).mapRoleDtoOutFromRoleEntity(saved);
    }


    @Test
    void findRoleByName() {
        RoleEntity entity = mock(RoleEntity.class);

        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(entity));

        Set<RoleEntity> result = roleService.findRoleByName(ROLE_NAME);

        assertEquals(1, result.size());

        verify(roleRepository).findByName(ROLE_NAME);
    }

    @Test
    void findRoleById() {
        RoleEntity entity = mock(RoleEntity.class);
        RoleDto dto = mock(RoleDto.class);

        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(roleMapper.mapDtoFromEntity(any(RoleEntity.class))).thenReturn(dto);

        RoleDto result = roleService.findRoleById(ID);
        assertEquals(result, dto);

        verify(roleRepository).findById(ID);
        verify(roleMapper).mapDtoFromEntity(entity);
    }

    @Test
    void throwExceptionIfRoleNotFound() {
        var ex = assertThrows(NotFoundModelException.class, () -> roleService.findRoleById(ID));
        assertEquals(ex.getMessage(), (String.format("Role with id = %s not found", ID)));

        verify(roleRepository).findById(ID);
    }


    @Test
    void findRolesAll() {
        RoleEntity entity = mock(RoleEntity.class);
        List<RoleEntity> entities = Arrays.asList(entity, entity, entity);

        RoleDto dto = mock(RoleDto.class);
        List<RoleDto> dtos = Arrays.asList(dto, dto, dto);

        when(roleRepository.findAll()).thenReturn(entities);
        when(roleMapper.mapListDtoFromListEntity(anyList())).thenReturn(dtos);

        List<RoleDto> result = roleService.findRolesAll();

        assertArrayEquals(result.toArray(), dtos.toArray());

        verify(roleRepository).findAll();
        verify(roleMapper).mapListDtoFromListEntity(entities);
    }

    @Test
    void updateRoleById() {
    }

    @Test
    void deleteRoleById() {
        RoleEntity entity = mock(RoleEntity.class);

        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(entity));

        roleService.deleteRoleById(ID);

        verify(roleRepository).findById(ID);
        verify(roleRepository).deleteById(ID);
    }
}