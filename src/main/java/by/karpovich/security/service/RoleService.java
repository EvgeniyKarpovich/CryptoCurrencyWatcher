package by.karpovich.security.service;

import by.karpovich.security.api.dto.PageResponse;
import by.karpovich.security.api.dto.role.RoleDto;
import org.springframework.data.domain.Pageable;

public interface RoleService {

    RoleDto save(RoleDto dto);

    RoleDto findById(Long id);

    PageResponse<RoleDto> findAll(Pageable pageable);

    RoleDto updateById(Long id, RoleDto dto);

    void deleteById(Long id);
}
