package by.karpovich.security.api.facades;

import by.karpovich.security.api.dto.role.RoleDto;
import by.karpovich.security.api.dto.role.RoleFullDtoOut;
import by.karpovich.security.jpa.entity.RoleEntity;
import by.karpovich.security.service.RoleService;
import by.karpovich.security.service.RoleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RoleFacade implements RoleService {

    private final RoleServiceImpl roleService;

    @Override
    public RoleFullDtoOut saveRole(RoleDto dto) {
        return roleService.saveRole(dto);
    }

    @Override
    public RoleDto findRoleById(Long id) {
        return roleService.findRoleById(id);
    }

    @Override
    public List<RoleDto> findRolesAll() {
        return roleService.findRolesAll();
    }

    @Override
    public RoleDto updateRoleById(Long id, RoleDto dto) {
        return roleService.updateRoleById(id, dto);
    }

    @Override
    public void deleteRoleById(Long id) {
        roleService.deleteRoleById(id);
    }
}

