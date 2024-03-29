package by.karpovich.security.service;

import by.karpovich.security.api.dto.PageResponse;
import by.karpovich.security.api.dto.authentification.JwtResponse;
import by.karpovich.security.api.dto.authentification.LoginForm;
import by.karpovich.security.api.dto.user.UserDtoForCreateUpdate;
import by.karpovich.security.api.dto.user.UserDtoForFindAll;
import by.karpovich.security.api.dto.user.UserDtoFullOut;
import by.karpovich.security.api.dto.user.UserFilter;
import by.karpovich.security.exception.NotFoundModelException;
import by.karpovich.security.jpa.entity.UserEntity;
import by.karpovich.security.jpa.entity.UserStatus;
import by.karpovich.security.jpa.querydsl.QPredicates;
import by.karpovich.security.jpa.repository.UserRepository;
import by.karpovich.security.mapping.JwtResponseMapper;
import by.karpovich.security.mapping.UserMapper;
import by.karpovich.security.security.JwtUtils;
import by.karpovich.security.utils.Utils;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static by.karpovich.security.jpa.entity.QUserEntity.userEntity;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final UserMapper userMapper;
    private final JwtResponseMapper jwtResponseMapper;
    private final ImageService imageService;

    @Override
    @Transactional
    public UserDtoFullOut signUp(UserDtoForCreateUpdate userDto) {
        return Optional.of(userDto)
                .map(userMapper::mapEntityFromCreateUpdateDto)
                .map(userRepository::save)
                .map(userMapper::mapUserFullDtoFromEntity)
                .orElseThrow();
    }

    @Override
    @Transactional
    public JwtResponse signIn(LoginForm loginForm) {
        return jwtResponseMapper.mapJwtResponseFromLoginForm(loginForm);
    }

    @Override
    public PageResponse<UserDtoForFindAll> findByPredicates(UserFilter filter, Pageable pageable) {
        Predicate predicate = QPredicates.builder()
                .add(filter.username(), userEntity.username::containsIgnoreCase)
                .build();

        Page<UserDtoForFindAll> usersDto = userRepository.findAll(predicate, pageable)
                .map(userMapper::mapUserDtoForFindAllFromEntity);

        return PageResponse.of(usersDto);
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
    public UserDtoFullOut updateById(String token, UserDtoForCreateUpdate dto) {
        var entity = userMapper.mapEntityFromCreateUpdateDto(dto);
        entity.setId(getUserIdFromToken(token));
        var updatedEntity = userRepository.save(entity);

        return userMapper.mapUserFullDtoFromEntity(updatedEntity);
    }

    @Override
    public PageResponse<UserDtoForFindAll> findAll(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("username").descending());
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

    @Override
    public Optional<byte[]> findAvatar(String token) {
        return userRepository.findById(getUserIdFromToken(token))
                .map(UserEntity::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
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
