package by.karpovich.security.service;

import by.karpovich.security.api.dto.PageResponse;
import by.karpovich.security.api.dto.authentification.JwtResponse;
import by.karpovich.security.api.dto.authentification.LoginForm;
import by.karpovich.security.api.dto.authentification.RegistrationForm;
import by.karpovich.security.api.dto.user.UserDtoForFindAll;
import by.karpovich.security.api.dto.user.UserDtoForUpdate;
import by.karpovich.security.api.dto.user.UserDtoFullOut;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    void signUp(RegistrationForm dto);

    JwtResponse signIn(LoginForm loginForm);

    UserDtoFullOut findById(Long id);

    UserDtoFullOut getYourselfBack(String token);

    UserDtoFullOut updateById(String token, UserDtoForUpdate dto);

    PageResponse<UserDtoForFindAll> findAll(Pageable pageable);

    List<UserDtoForFindAll> findByStatus(String status);

    void setStatus(Long id, String status);

    void addImage(String token, MultipartFile file);

}
