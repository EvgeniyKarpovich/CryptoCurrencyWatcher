package by.karpovich.CryptoWatcher.service;

import by.karpovich.CryptoWatcher.api.dto.authentification.JwtResponse;
import by.karpovich.CryptoWatcher.api.dto.authentification.LoginForm;
import by.karpovich.CryptoWatcher.api.dto.authentification.RegistrationForm;
import by.karpovich.CryptoWatcher.api.dto.user.UserDtoForFindAll;
import by.karpovich.CryptoWatcher.api.dto.user.UserForUpdate;
import by.karpovich.CryptoWatcher.api.dto.user.UserFullDtoOut;
import by.karpovich.CryptoWatcher.exception.NotFoundModelException;
import by.karpovich.CryptoWatcher.jpa.entity.UserEntity;
import by.karpovich.CryptoWatcher.jpa.entity.UserStatus;
import by.karpovich.CryptoWatcher.jpa.repository.UserRepository;
import by.karpovich.CryptoWatcher.mapping.JwtResponseMapper;
import by.karpovich.CryptoWatcher.mapping.UserMapper;
import by.karpovich.CryptoWatcher.security.JwtUtils;
import by.karpovich.CryptoWatcher.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public UserFullDtoOut getYourselfBack(String token) {
        UserEntity userEntity = findUserEntityByIdFromToken(token);
        return userMapper.mapUserFullDtoFromEntity(userEntity);
    }

    @Override
    public UserFullDtoOut findById(Long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        UserEntity userEntity = optionalUser.orElseThrow(
                () -> new NotFoundModelException(String.format("User with id = %s found", id))
        );

        return userMapper.mapUserFullDtoFromEntity(userEntity);
    }

    @Override
    @Transactional
    public UserFullDtoOut updateUserById(String token, UserForUpdate dto) {
        Long userIdFromToken = getUserIdFromToken(token);

        UserEntity user = userMapper.mapEntityFromUpdateDto(dto);
        user.setId(userIdFromToken);
        UserEntity updatedUser = userRepository.save(user);

        return userMapper.mapUserFullDtoFromEntity(updatedUser);
    }

    @Override
    public Map<String, Object> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateOfCreation").descending());
        Page<UserEntity> usersEntity = userRepository.findAll(pageable);
        List<UserEntity> content = usersEntity.getContent();

        List<UserDtoForFindAll> usersDto = userMapper.mapListUserDtoForFindAllFromListEntity(content);

        Map<String, Object> response = new HashMap<>();
        response.put("Users", usersDto);
        response.put("currentPage", usersEntity.getNumber());
        response.put("totalItems", usersEntity.getTotalElements());
        response.put("totalPages", usersEntity.getTotalPages());

        return response;
    }

    @Override
    public List<UserDtoForFindAll> getUsersByStatus(String status) {
        UserStatus userStatus = switch(status.toUpperCase()) {
            case "ACTIVE" -> UserStatus.ACTIVE;
            case "FROZEN" -> UserStatus.FROZEN;
            case "DELETED" -> UserStatus.DELETED;
            default -> throw new IllegalArgumentException("Invalid user status: " + status);
        };

        List<UserEntity> userByStatus = userRepository.findByUserStatus(userStatus);

        return userMapper.mapListUserDtoForFindAllFromListEntity(userByStatus);
    }

    @Override
    @Transactional
    public void deleteUserById(String token) {
        UserEntity userEntity = findUserEntityByIdFromToken(token);

        userEntity.setUserStatus(UserStatus.DELETED);
    }

    @Override
    @Transactional
    public void addImage(String token, MultipartFile file) {
        UserEntity userEntity = findUserEntityByIdFromToken(token);

        userEntity.setImage(Utils.saveFile(file));
        userRepository.save(userEntity);
    }

    @Override
    public UserEntity findUserByIdWhichWillReturnModel(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundModelException(String.format("User with username = %s not found", id))
        );
    }

    private UserEntity findUserEntityByIdFromToken(String token) {
        Long userIdFromToken = getUserIdFromToken(token);

        return findUserByIdWhichWillReturnModel(userIdFromToken);
    }

    private Long getUserIdFromToken(String authorization) {
        String token = authorization.substring(7);
        String userIdFromJWT = jwtUtils.getUserIdFromJWT(token);
        return Long.parseLong(userIdFromJWT);
    }
}
