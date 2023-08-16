package by.karpovich.security.api.controllers.adminController;

import by.karpovich.security.api.dto.role.RoleDto;
import by.karpovich.security.service.RoleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/roles")
@RequiredArgsConstructor
public class AdminRolesController {

    private final RoleServiceImpl roleServiceImpl;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody RoleDto dto) {
        roleServiceImpl.saveRole(dto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        RoleDto roleDto = roleServiceImpl.findRoleById(id);

        return new ResponseEntity<>(roleDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<RoleDto> rolesDto = roleServiceImpl.findRolesAll();

        return new ResponseEntity<>(rolesDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody RoleDto dto,
                                    @PathVariable("id") Long id) {
        RoleDto roleDto = roleServiceImpl.updateRoleById(id, dto);

        return new ResponseEntity<>(roleDto, HttpStatus.UPGRADE_REQUIRED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        roleServiceImpl.deleteRoleById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
