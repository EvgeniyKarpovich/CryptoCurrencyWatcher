package by.karpovich.security.api.controllers.clientController;

import by.karpovich.security.api.dto.user.UserDtoForCreateUpdate;
import by.karpovich.security.api.dto.user.UserDtoFullOut;
import by.karpovich.security.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "UserController", description = "api for users(client)")
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping
    public ResponseEntity<?> getYourselfBack(/*@ApiParam(value = "auth token") */@RequestHeader(value = "Authorization") String token) {
        UserDtoFullOut userDtoFullOut = userService.getYourselfBack(token);

        return new ResponseEntity<>(userDtoFullOut, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(  @Valid @RequestBody UserDtoForCreateUpdate dto,
                                    @PathVariable("id") String token) {
        userService.updateById(token, dto);

        return new ResponseEntity<>(String.format("%s  successfully updated", dto.getUsername()), HttpStatus.UPGRADE_REQUIRED);
    }

    @PostMapping("/{id}/{status}")
    public ResponseEntity<?> setStatus(@PathVariable("id") Long id, @PathVariable("status") String status) {
        userService.setStatus(id, status);

        return new ResponseEntity<>("User successfully deleted", HttpStatus.OK);
    }

    @GetMapping(value = "/avatar")
    public ResponseEntity<byte[]> findAvatar(@RequestHeader(value = "Authorization") String token) {
        return userService.findAvatar(token)
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(notFound()::build);
    }

    @PutMapping(value = "/images")
    public ResponseEntity<?> addImage(@RequestHeader(value = "Authorization") String authorization,
                                      @RequestPart("file") MultipartFile file) {
        userService.addImage(authorization, file);

        return new ResponseEntity<>(HttpStatus.UPGRADE_REQUIRED);
    }
}
