package by.karpovich.security.service;

import by.karpovich.security.api.dto.role.RoleDto;
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

        RoleDto returned = mock(RoleDto.class);
        when(roleMapper.mapDtoFromEntity(any(RoleEntity.class))).thenReturn(returned);

        RoleDto dto = new RoleDto();

        RoleDto result = roleService.save(dto);
        assertEquals(result, returned);

        verify(roleRepository).findByName(dto.getName());
        verify(roleMapper).mapEntityFromDto(dto);
        verify(roleRepository).save(mapped);
        verify(roleMapper).mapDtoFromEntity(saved);
    }


    @Test
    void findRoleByName() {
        RoleEntity entity = mock(RoleEntity.class);

        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(entity));

        Set<RoleEntity> result = roleService.findByName(ROLE_NAME);

        assertEquals(1, result.size());

        verify(roleRepository).findByName(ROLE_NAME);
    }

    @Test
    void findRoleById() {
        RoleEntity entity = mock(RoleEntity.class);
        RoleDto dto = mock(RoleDto.class);

        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(roleMapper.mapDtoFromEntity(any(RoleEntity.class))).thenReturn(dto);

        RoleDto result = roleService.findById(ID);
        assertEquals(result, dto);

        verify(roleRepository).findById(ID);
        verify(roleMapper).mapDtoFromEntity(entity);
    }

    @Test
    void throwExceptionIfRoleNotFound() {
        var ex = assertThrows(NotFoundModelException.class, () -> roleService.findById(ID));
        assertEquals(ex.getMessage(), (String.format("Role with id = %s not found", ID)));

        verify(roleRepository).findById(ID);
    }


//    @Test
//    void findRolesAll() {
//        RoleEntity entity = mock(RoleEntity.class);
//        List<RoleEntity> entities = Arrays.asList(entity, entity, entity);
//
//        RoleDto dto = mock(RoleDto.class);
//        List<RoleDto> dtos = Arrays.asList(dto, dto, dto);
//
//        when(roleRepository.findAll()).thenReturn(entities);
//        when(roleMapper.mapListDtoFromListEntity(anyList())).thenReturn(dtos);
//
//        List<RoleDto> result = roleService.findAll();
//
//        assertArrayEquals(result.toArray(), dtos.toArray());
//
//        verify(roleRepository).findAll();
//        verify(roleMapper).mapListDtoFromListEntity(entities);
//    }

    @Test
    void updateRoleById() {
        RoleEntity mapped = mock(RoleEntity.class);
        RoleEntity updated = mock(RoleEntity.class);
        RoleDto dtoOut = mock(RoleDto.class);
        RoleDto dtoForUpdate = mock(RoleDto.class);

        when(roleMapper.mapEntityFromDto(any(RoleDto.class))).thenReturn(mapped);
        when(roleRepository.save(any(RoleEntity.class))).thenReturn(updated);
//        Mockito.doReturn(updated).when(roleRepository).save(Mockito.any());
        when(roleMapper.mapDtoFromEntity(any(RoleEntity.class))).thenReturn(dtoOut);


        RoleDto result = roleService.updateById(ID, dtoForUpdate);

        assertEquals(result, dtoOut);

        verify(roleRepository).findByName(dtoForUpdate.getName());
        verify(roleMapper).mapEntityFromDto(dtoForUpdate);
        verify(roleRepository).save(mapped);
        verify(roleMapper).mapDtoFromEntity(updated);
    }

    @Test
    void deleteRoleById() {
        RoleEntity entity = mock(RoleEntity.class);

        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(entity));

        roleService.deleteById(ID);

        verify(roleRepository).findById(ID);
        verify(roleRepository).deleteById(ID);
    }
}