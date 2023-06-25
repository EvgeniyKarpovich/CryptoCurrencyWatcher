package by.karpovich.cryptoWatcher.api.adminController;

import by.karpovich.cryptoWatcher.api.dto.user.UserDtoForFindAll;
import by.karpovich.cryptoWatcher.api.dto.user.UserFullDtoOut;
import by.karpovich.cryptoWatcher.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUsersController {

    private final UserServiceImpl userService;

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "20") int size) {
        Map<String, Object> usersDto = userService.findAll(page, size);

        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        UserFullDtoOut userFullDtoOut = userService.findById(id);

        return new ResponseEntity<>(userFullDtoOut, HttpStatus.OK);
    }

    @GetMapping("/statuses/{status}")
    public ResponseEntity<?> getUsersByStatus(@PathVariable("status") String status) {
        List<UserDtoForFindAll> usersByStatus = userService.getUsersByStatus(status);

        return new ResponseEntity<>(usersByStatus, HttpStatus.OK);
    }
}
