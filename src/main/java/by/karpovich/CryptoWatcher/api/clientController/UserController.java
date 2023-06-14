package by.karpovich.CryptoWatcher.api.clientController;

import by.karpovich.CryptoWatcher.api.dto.user.UserForUpdate;
import by.karpovich.CryptoWatcher.api.dto.user.UserFullDtoOut;
import by.karpovich.CryptoWatcher.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @GetMapping
    public ResponseEntity<?> findById(@RequestHeader(value = "Authorization") String token) {
        UserFullDtoOut userFullDtoOut = userServiceImpl.getYourselfBack(token);

        return new ResponseEntity<>(userFullDtoOut, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody UserForUpdate dto,
                                    @PathVariable("id") String token) {
        userServiceImpl.updateUserById(token, dto);

        return new ResponseEntity<>(String.format("%s  successfully updated", dto.getUsername()), HttpStatus.UPGRADE_REQUIRED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String token) {
        userServiceImpl.deleteUserById(token);

        return new ResponseEntity<>("User successfully deleted", HttpStatus.OK);
    }

    @PostMapping(value = "/images/{postId}")
    public ResponseEntity<?> addImage(@RequestHeader(value = "Authorization") String authorization,
                                      @RequestPart("file") MultipartFile file) {
        userServiceImpl.addImage(authorization, file);

        return new ResponseEntity<>(HttpStatus.UPGRADE_REQUIRED);
    }
}
