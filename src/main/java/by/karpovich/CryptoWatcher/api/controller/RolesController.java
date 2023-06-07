package by.karpovich.CryptoWatcher.api.controller;

import by.karpovich.CryptoWatcher.api.dto.role.RoleDto;
import by.karpovich.CryptoWatcher.jpa.entity.RoleEntity;
import by.karpovich.CryptoWatcher.service.RoleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolesController {

    private final RoleServiceImpl roleServiceImpl;

    @PostMapping
    public RoleEntity save(@RequestBody RoleDto dto) {
        return roleServiceImpl.saveRole(dto);
    }

    @GetMapping("/{id}")
    public RoleDto findById(@PathVariable("id") Long id) {
        return roleServiceImpl.findRoleById(id);
    }

    @GetMapping
    public List<RoleDto> findAll() {
        return roleServiceImpl.findRolesAll();
    }

    @PutMapping("/{id}")
    public RoleDto update(@RequestBody RoleDto dto,
                          @PathVariable("id") Long id) {
        return roleServiceImpl.updateRoleById(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        roleServiceImpl.deleteRoleById(id);
    }
}
