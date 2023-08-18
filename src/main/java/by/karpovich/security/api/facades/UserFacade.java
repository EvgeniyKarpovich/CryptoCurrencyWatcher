package by.karpovich.security.api.facades;

import by.karpovich.security.api.dto.authentification.JwtResponse;
import by.karpovich.security.api.dto.authentification.LoginForm;
import by.karpovich.security.api.dto.authentification.RegistrationForm;
import by.karpovich.security.api.dto.user.UserDtoForFindAll;
import by.karpovich.security.api.dto.user.UserDtoForUpdate;
import by.karpovich.security.api.dto.user.UserDtoFullOut;
import by.karpovich.security.service.UserService;
import by.karpovich.security.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserFacade implements UserService {

    private final UserServiceImpl userService;

    @Override
    public void signUp(RegistrationForm dto) {
        userService.signUp(dto);
    }

    @Override
    public JwtResponse signIn(LoginForm loginForm) {
        return userService.signIn(loginForm);
    }

    @Override
    public UserDtoFullOut getYourselfBack(String token) {
        return userService.getYourselfBack(token);
    }

    @Override
    public UserDtoFullOut findById(Long id) {
        return userService.findById(id);
    }

    @Override
    public UserDtoFullOut updateUserById(String token, UserDtoForUpdate dto) {
        return userService.updateUserById(token, dto);
    }

    @Override
    public Map<String, Object> findAll(int page, int size) {
        return userService.findAll(page, size);
    }

    @Override
    public List<UserDtoForFindAll> getUsersByStatus(String status) {
        return userService.getUsersByStatus(status);
    }

    @Override
    public void deleteUserById(String token) {
        userService.deleteUserById(token);
    }

    @Override
    public void addImage(String token, MultipartFile file) {
        userService.addImage(token, file);
    }


}
