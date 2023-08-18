package by.karpovich.security.api.controllers.clientController;

import by.karpovich.security.api.dto.user.UserDtoForUpdate;
import by.karpovich.security.api.dto.user.UserDtoFullOut;
import by.karpovich.security.api.facades.UserFacade;
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

    private final UserFacade userFacade;

    @GetMapping
    public ResponseEntity<?> findById(@RequestHeader(value = "Authorization") String token) {
        UserDtoFullOut userDtoFullOut = userFacade.getYourselfBack(token);

        return new ResponseEntity<>(userDtoFullOut, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody UserDtoForUpdate dto,
                                    @PathVariable("id") String token) {
        userFacade.updateUserById(token, dto);

        return new ResponseEntity<>(String.format("%s  successfully updated", dto.getUsername()), HttpStatus.UPGRADE_REQUIRED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String token) {
        userFacade.deleteUserById(token);

        return new ResponseEntity<>("User successfully deleted", HttpStatus.OK);
    }

    @PostMapping(value = "/images/{postId}")
    public ResponseEntity<?> addImage(@RequestHeader(value = "Authorization") String authorization,
                                      @RequestPart("file") MultipartFile file) {
        userFacade.addImage(authorization, file);

        return new ResponseEntity<>(HttpStatus.UPGRADE_REQUIRED);
    }
}
