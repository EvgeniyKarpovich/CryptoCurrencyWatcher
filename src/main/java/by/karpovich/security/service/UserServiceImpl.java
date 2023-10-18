package by.karpovich.security.service;

import by.karpovich.security.api.dto.PageResponse;
import by.karpovich.security.api.dto.authentification.JwtResponse;
import by.karpovich.security.api.dto.authentification.LoginForm;
import by.karpovich.security.api.dto.authentification.RegistrationForm;
import by.karpovich.security.api.dto.user.UserDtoForFindAll;
import by.karpovich.security.api.dto.user.UserDtoForUpdate;
import by.karpovich.security.api.dto.user.UserDtoFullOut;
import by.karpovich.security.exception.NotFoundModelException;
import by.karpovich.security.jpa.entity.UserEntity;
import by.karpovich.security.jpa.entity.UserStatus;
import by.karpovich.security.jpa.repository.UserRepository;
import by.karpovich.security.mapping.JwtResponseMapper;
import by.karpovich.security.mapping.UserMapper;
import by.karpovich.security.security.JwtUtils;
import by.karpovich.security.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final UserMapper userMapper;
    private final JwtResponseMapper jwtResponseMapper;

    @Override
    @Transactional
    public void signUp(RegistrationForm dto) {
        userRepository.save(userMapper.mapEntityFromDtoForRegForm(dto));
    }

    @Override
    @Transactional
    public JwtResponse signIn(LoginForm loginForm) {
        return jwtResponseMapper.mapJwtResponseFromLoginForm(loginForm);
    }

    @Override
    public UserDtoFullOut getYourselfBack(String token) {
        return userMapper.mapUserFullDtoFromEntity(findUserByIdFromToken(token));
    }

    @Override
    public UserDtoFullOut findById(Long id) {
        var entity = userRepository.findById(id).orElseThrow(
                () -> new NotFoundModelException(String.format("User with id = %s found", id))
        );
        return userMapper.mapUserFullDtoFromEntity(entity);
    }

    @Override
    @Transactional
    public UserDtoFullOut updateById(String token, UserDtoForUpdate dto) {
        var entity = userMapper.mapEntityFromUpdateDto(dto);
        entity.setId(getUserIdFromToken(token));
        var updatedEntity = userRepository.save(entity);

        return userMapper.mapUserFullDtoFromEntity(updatedEntity);
    }

    @Override
    public PageResponse<UserDtoForFindAll> findAll(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("dateOfCreation").descending());
        Page<UserDtoForFindAll> dtos = userRepository.findAll(pageRequest)
                .map(userMapper::mapUserDtoForFindAllFromEntity);

        return PageResponse.of(dtos);
    }

    @Override
    public List<UserDtoForFindAll> findByStatus(String status) {
        UserStatus userStatus = switch (status.toUpperCase()) {
            case "ACTIVE" -> UserStatus.ACTIVE;
            case "FROZEN" -> UserStatus.FROZEN;
            case "DELETED" -> UserStatus.DELETED;
            default -> throw new IllegalArgumentException("Invalid user status: " + status);
        };

        return userMapper.mapListUserDtoForFindAllFromListEntity(userRepository.findByStatus(userStatus));
    }

    @Override
    @Transactional
    public void setStatus(Long id, String status) {
        if (userRepository.findById(id).isPresent()) {
            UserStatus userStatus = switch (status.toUpperCase()) {
                case "ACTIVE" -> UserStatus.ACTIVE;
                case "FROZEN" -> UserStatus.FROZEN;
                case "DELETED" -> UserStatus.DELETED;
                default -> throw new IllegalArgumentException("Invalid user status: " + status);
            };
            userRepository.setStatus(id, userStatus);
        } else {
            throw new NotFoundModelException(String.format("UserEntity with id = %s not found", id));
        }
    }

    @Override
    @Transactional
    public void addImage(String token, MultipartFile file) {
        var entity = findUserByIdFromToken(token);

        entity.setImage(Utils.saveFile(file));
        userRepository.save(entity);
    }

    private UserEntity findUserByIdWhichWillReturnModel(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundModelException(String.format("User with username = %s not found", id)));
    }

    private UserEntity findUserByIdFromToken(String token) {
        return findUserByIdWhichWillReturnModel(getUserIdFromToken(token));
    }

    private Long getUserIdFromToken(String authorization) {
        String token = authorization.substring(7);
        String userIdFromJWT = jwtUtils.getUserIdFromJWT(token);
        return Long.parseLong(userIdFromJWT);
    }
}
