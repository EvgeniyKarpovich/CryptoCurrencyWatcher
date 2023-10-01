package by.karpovich.security.api.controllers.clientController;

import by.karpovich.security.api.dto.authentification.JwtResponse;
import by.karpovich.security.api.dto.authentification.LoginForm;
import by.karpovich.security.api.dto.authentification.RegistrationForm;
import by.karpovich.security.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserServiceImpl userService;

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody LoginForm loginForm) {
        JwtResponse jwtResponse = userService.signIn(loginForm);

        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody RegistrationForm signUpRequest) {
        userService.signUp(signUpRequest);

        return new ResponseEntity<>(String.format("%s registered successfully!", signUpRequest.getUsername()), HttpStatus.CREATED);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}