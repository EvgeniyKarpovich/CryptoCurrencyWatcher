package by.karpovich.cryptoWatcher.api.adminController;

import by.karpovich.cryptoWatcher.api.dto.role.RoleDto;
import by.karpovich.cryptoWatcher.jpa.entity.RoleEntity;
import by.karpovich.cryptoWatcher.service.RoleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/roles")
@RequiredArgsConstructor
public class RolesController {

    private final RoleServiceImpl roleServiceImpl;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody RoleDto dto) {
        RoleEntity roleEntity = roleServiceImpl.saveRole(dto);

        return new ResponseEntity<>(roleEntity, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        RoleDto roleById = roleServiceImpl.findRoleById(id);

        return new ResponseEntity<>(roleById, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<RoleDto> roles = roleServiceImpl.findRolesAll();

        return new ResponseEntity<>(roles, HttpStatus.OK);
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
