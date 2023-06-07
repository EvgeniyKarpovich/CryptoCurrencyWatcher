package by.karpovich.CryptoWatcher.service;

import by.karpovich.CryptoWatcher.api.dto.authentification.JwtResponse;
import by.karpovich.CryptoWatcher.api.dto.authentification.LoginForm;
import by.karpovich.CryptoWatcher.api.dto.authentification.RegistrationForm;
import by.karpovich.CryptoWatcher.jpa.entity.UserEntity;

public interface UserService {

    void signUp(RegistrationForm dto);

    JwtResponse signIn(LoginForm loginForm);

    UserEntity findUserByName(String username);

    UserEntity findUserByIdWhichWillReturnModel(Long id);
}
