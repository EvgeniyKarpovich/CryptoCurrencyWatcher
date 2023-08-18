package by.karpovich.security.service;

import by.karpovich.security.api.dto.role.RoleDto;
import by.karpovich.security.api.dto.role.RoleFullDtoOut;

import java.util.List;

public interface RoleService {

    RoleFullDtoOut saveRole(RoleDto dto);

    RoleDto findRoleById(Long id);

    List<RoleDto> findRolesAll();

    RoleDto updateRoleById(Long id, RoleDto dto);

    void deleteRoleById(Long id);
}
