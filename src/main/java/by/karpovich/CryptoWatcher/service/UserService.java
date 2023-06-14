package by.karpovich.CryptoWatcher.service;

import by.karpovich.CryptoWatcher.api.dto.authentification.JwtResponse;
import by.karpovich.CryptoWatcher.api.dto.authentification.LoginForm;
import by.karpovich.CryptoWatcher.api.dto.authentification.RegistrationForm;
import by.karpovich.CryptoWatcher.api.dto.user.UserDtoForFindAll;
import by.karpovich.CryptoWatcher.api.dto.user.UserForUpdate;
import by.karpovich.CryptoWatcher.api.dto.user.UserFullDtoOut;
import by.karpovich.CryptoWatcher.jpa.entity.UserEntity;
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
