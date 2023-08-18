package by.karpovich.security.api.controllers.adminController;

import by.karpovich.security.api.facades.RoleFacade;
import by.karpovich.security.api.dto.role.RoleDto;
import by.karpovich.security.api.dto.role.RoleFullDtoOut;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/roles")
@RequiredArgsConstructor
public class AdminRolesController {

    private final RoleFacade rolesFacade;

    @PostMapping
    public ResponseEntity<RoleFullDtoOut> save(@RequestBody RoleDto dto) {
        RoleFullDtoOut roleDtoOut = rolesFacade.saveRole(dto);

        return new ResponseEntity<>(roleDtoOut, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> findById(@PathVariable("id") Long id) {
        RoleDto roleDto = rolesFacade.findRoleById(id);

        return new ResponseEntity<>(roleDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RoleDto>> findAll() {
        List<RoleDto> rolesDto = rolesFacade.findRolesAll();

        return new ResponseEntity<>(rolesDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> update(@RequestBody RoleDto dto,
                                          @PathVariable("id") Long id) {
        RoleDto roleDto = rolesFacade.updateRoleById(id, dto);

        return new ResponseEntity<>(roleDto, HttpStatus.UPGRADE_REQUIRED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        rolesFacade.deleteRoleById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
