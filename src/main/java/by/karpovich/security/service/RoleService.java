package by.karpovich.security.service;

import by.karpovich.security.api.dto.role.RoleDto;

import java.util.List;

public interface RoleService {

    RoleDto save(RoleDto dto);

    RoleDto findById(Long id);

    List<RoleDto> findAll();

    RoleDto updateById(Long id, RoleDto dto);

    void deleteById(Long id);
}
