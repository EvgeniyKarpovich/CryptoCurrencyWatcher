package by.karpovich.security.service;

import by.karpovich.security.api.dto.PageResponse;
import by.karpovich.security.api.dto.authentification.JwtResponse;
import by.karpovich.security.api.dto.authentification.LoginForm;
import by.karpovich.security.api.dto.user.UserDtoForCreateUpdate;
import by.karpovich.security.api.dto.user.UserDtoForFindAll;
import by.karpovich.security.api.dto.user.UserDtoFullOut;
import by.karpovich.security.api.dto.user.UserFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDtoFullOut signUp(UserDtoForCreateUpdate dto);

    JwtResponse signIn(LoginForm loginForm);

    UserDtoFullOut findById(Long id);

    UserDtoFullOut getYourselfBack(String token);

    UserDtoFullOut updateById(String token, UserDtoForCreateUpdate dto);

    PageResponse<UserDtoForFindAll> findAll(Pageable pageable);

    List<UserDtoForFindAll> findByStatus(String status);

    void setStatus(Long id, String status);

    Optional<byte[]> findAvatar(String token);

    void addImage(String token, MultipartFile file);

    PageResponse<UserDtoForFindAll> findByPredicates(UserFilter filter, Pageable pageable);
}
