package by.karpovich.security.service;

import by.karpovich.security.api.dto.authentification.JwtResponse;
import by.karpovich.security.api.dto.authentification.LoginForm;
import by.karpovich.security.api.dto.authentification.RegistrationForm;
import by.karpovich.security.api.dto.user.UserDtoForFindAll;
import by.karpovich.security.api.dto.user.UserForUpdate;
import by.karpovich.security.api.dto.user.UserFullDtoOut;
import by.karpovich.security.jpa.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface UserService {

    void signUp(RegistrationForm dto);

    JwtResponse signIn(LoginForm loginForm);

    void deleteUserById(String token);

    UserFullDtoOut getYourselfBack(String token);

    UserFullDtoOut updateUserById(String token, UserForUpdate dto);

    List<UserDtoForFindAll> getUsersByStatus(String status);

    UserFullDtoOut findById(Long id);

    void addImage(String authorization, MultipartFile file);

    Map<String, Object> findAll(int page, int size);

    UserEntity findUserByIdWhichWillReturnModel(Long id);
}
