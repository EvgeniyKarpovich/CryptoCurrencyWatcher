package by.karpovich.CryptoWatcher.service;

import by.karpovich.CryptoWatcher.api.dto.authentification.JwtResponse;
import by.karpovich.CryptoWatcher.api.dto.authentification.LoginForm;
import by.karpovich.CryptoWatcher.api.dto.authentification.RegistrationForm;
import by.karpovich.CryptoWatcher.exception.NotFoundModelException;
import by.karpovich.CryptoWatcher.jpa.entity.UserEntity;
import by.karpovich.CryptoWatcher.jpa.repository.UserRepository;
import by.karpovich.CryptoWatcher.mapping.UserMapper;
import by.karpovich.CryptoWatcher.security.JwtUtils;
import by.karpovich.CryptoWatcher.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public void signUp(RegistrationForm dto) {

        userRepository.save(userMapper.mapEntityFromDtoForRegForm(dto));
    }

    @Override
    @Transactional
    public JwtResponse signIn(LoginForm loginForm) {
        String username = loginForm.getUsername();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));

        UserEntity userByName = findUserByName(username);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateToken(userByName.getUsername(), userByName.getId());

        return JwtResponse.builder()
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .roles(mapStringRolesFromUserDetails(userDetails))
                .type("Bearer")
                .token(jwt)
                .build();
    }

    @Override
    public UserEntity findUserByName(String username) {
        Optional<UserEntity> userByName = userRepository.findByUsername(username);

        UserEntity entity = userByName.orElseThrow(
                () -> new NotFoundModelException(String.format("User with username = %s not found", username))
        );
        return entity;
    }

    @Override
    public UserEntity findUserByIdWhichWillReturnModel(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundModelException(String.format("User with username = %s not found", id))
        );
    }

    public UserEntity findUserEntityByIdFromToken(String token) {
        Long userIdFromToken = getUserIdFromToken(token);

        return findUserByIdWhichWillReturnModel(userIdFromToken);
    }

    public Long getUserIdFromToken(String authorization) {
        String token = authorization.substring(7);
        String userIdFromJWT = jwtUtils.getUserIdFromJWT(token);
        return Long.parseLong(userIdFromJWT);
    }


    private List<String> mapStringRolesFromUserDetails(UserDetailsImpl userDetails) {
        return userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}
