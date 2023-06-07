package by.karpovich.CryptoWatcher.service;

import by.karpovich.CryptoWatcher.api.dto.role.RoleDto;
import by.karpovich.CryptoWatcher.jpa.entity.RoleEntity;

import java.util.List;
import java.util.Set;

public interface RoleService {

    RoleEntity saveRole(RoleDto dto);

    Set<RoleEntity> findRoleByName(String roleName);

    RoleDto findRoleById(Long id);

    RoleDto updateRoleById(Long id, RoleDto dto);

    List<RoleDto> findRolesAll();

    void deleteRoleById(Long id);
}
