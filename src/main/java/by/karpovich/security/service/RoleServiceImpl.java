package by.karpovich.security.service;

import by.karpovich.security.api.dto.role.RoleDto;
import by.karpovich.security.api.dto.role.RoleFullDtoOut;
import by.karpovich.security.exception.DuplicateException;
import by.karpovich.security.exception.NotFoundModelException;
import by.karpovich.security.jpa.entity.RoleEntity;
import by.karpovich.security.jpa.repository.RoleRepository;
import by.karpovich.security.mapping.RoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    @Transactional
    public RoleFullDtoOut saveRole(RoleDto dto) {
        validateAlreadyExists(dto, null);

        return Optional.of(dto)
                .map(roleMapper::mapEntityFromDto)
                .map(roleRepository::save)
                .map(roleMapper::mapRoleDtoOutFromRoleEntity)
                .orElseThrow();
    }

    public Set<RoleEntity> findRoleByName(String roleName) {
        Optional<RoleEntity> role = roleRepository.findByName(roleName);

        var roleEntity = role.orElseThrow(
                () -> new NotFoundModelException(String.format("Role with name = %s not found", role)));

        Set<RoleEntity> userRoles = new HashSet<>();
        userRoles.add(roleEntity);

        return userRoles;
    }

    @Override
    public RoleDto findRoleById(Long id) {
        var role = roleRepository.findById(id).orElseThrow(
                () -> new NotFoundModelException(String.format("Role with id = %s not found", id)));

        return roleMapper.mapDtoFromEntity(role);
    }

    @Override
    public List<RoleDto> findRolesAll() {
        return roleMapper.mapListDtoFromListEntity(roleRepository.findAll());
    }

    @Override
    @Transactional
    public RoleDto updateRoleById(Long id, RoleDto dto) {
        validateAlreadyExists(dto, id);

        var entity = roleMapper.mapEntityFromDto(dto);
        entity.setId(id);
        var updated = roleRepository.save(entity);

        return roleMapper.mapDtoFromEntity(updated);
    }

    @Override
    @Transactional
    public void deleteRoleById(Long id) {
        if (roleRepository.findById(id).isPresent()) {
            roleRepository.deleteById(id);
        } else {
            throw new NotFoundModelException(String.format("Role with id = %s not found", id));
        }
    }

    private void validateAlreadyExists(RoleDto dto, Long id) {
        boolean present = roleRepository.findByName(dto.getName())
                .filter(role -> !role.getId().equals(id))
                .isPresent();

        if (present) {
            throw new DuplicateException(String.format("Role with name = %s already exist", dto.getName()));
        }
    }
}
