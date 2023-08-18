package by.karpovich.security.service;

import by.karpovich.security.api.dto.authentification.JwtResponse;
import by.karpovich.security.api.dto.authentification.LoginForm;
import by.karpovich.security.api.dto.authentification.RegistrationForm;
import by.karpovich.security.api.dto.user.UserDtoForFindAll;
import by.karpovich.security.api.dto.user.UserDtoForUpdate;
import by.karpovich.security.api.dto.user.UserDtoFullOut;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface UserService {

    void signUp(RegistrationForm dto);

    JwtResponse signIn(LoginForm loginForm);

    UserDtoFullOut findById(Long id);

    UserDtoFullOut getYourselfBack(String token);

    UserDtoFullOut updateUserById(String token, UserDtoForUpdate dto);

    Map<String, Object> findAll(int page, int size);

    List<UserDtoForFindAll> getUsersByStatus(String status);

    void deleteUserById(String token);

    void addImage(String token, MultipartFile file);

}
