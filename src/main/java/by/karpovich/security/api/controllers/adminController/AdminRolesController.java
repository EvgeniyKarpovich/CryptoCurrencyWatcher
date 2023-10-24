package by.karpovich.security.api.controllers.adminController;

import by.karpovich.security.api.dto.PageResponse;
import by.karpovich.security.api.dto.role.RoleDto;
import by.karpovich.security.service.RoleServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/roles")
@Tag(name = "AdminRolesController", description = "api for roles(admin)")
public class AdminRolesController {

    private final RoleServiceImpl roleService;

    @PostMapping
    public ResponseEntity<RoleDto> save(@RequestBody RoleDto dto) {
        RoleDto roleDtoOut = roleService.save(dto);

        return new ResponseEntity<>(roleDtoOut, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> findById(@PathVariable("id") Long id) {
        RoleDto roleDto = roleService.findById(id);

        return new ResponseEntity<>(roleDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageResponse<RoleDto>> findAll(Pageable pageable) {
        PageResponse<RoleDto> rolesDto = roleService.findAll(pageable);

        return new ResponseEntity<>(rolesDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> update(@RequestBody RoleDto dto,
                                          @PathVariable("id") Long id) {
        RoleDto roleDto = roleService.updateById(id, dto);

        return new ResponseEntity<>(roleDto, HttpStatus.UPGRADE_REQUIRED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        roleService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
