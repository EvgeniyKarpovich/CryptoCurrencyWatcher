package by.karpovich.security.api.controllers.adminController;

import by.karpovich.security.api.dto.PageResponse;
import by.karpovich.security.api.dto.user.UserDtoForFindAll;
import by.karpovich.security.api.dto.user.UserDtoFullOut;
import by.karpovich.security.api.dto.user.UserFilter;
import by.karpovich.security.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/users")
@Tag(name = "AdminUsersController", description = "api for users(admin)")
public class AdminUsersController {

    private final UserServiceImpl userService;

    @GetMapping
    public ResponseEntity<?> findAll(Pageable pageable) {
        PageResponse<UserDtoForFindAll> dtos = userService.findAll(pageable);

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        UserDtoFullOut userDtoFullOut = userService.findById(id);

        return new ResponseEntity<>(userDtoFullOut, HttpStatus.OK);
    }

    @GetMapping("/statuses/{status}")
    public ResponseEntity<?> findUsersByStatus(@PathVariable("status") String status) {
        List<UserDtoForFindAll> usersByStatus = userService.findByStatus(status);

        return new ResponseEntity<>(usersByStatus, HttpStatus.OK);
    }

    @GetMapping("/filters")
    public ResponseEntity<?> findByPredicate(@RequestBody UserFilter filter, Pageable pageable) {
        PageResponse<UserDtoForFindAll> byCriteria = userService.findByPredicates(filter, pageable);

        return new ResponseEntity<>(byCriteria, HttpStatus.OK);
    }
}
