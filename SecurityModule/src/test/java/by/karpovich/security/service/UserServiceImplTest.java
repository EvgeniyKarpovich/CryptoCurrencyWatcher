package by.karpovich.security.service;

import by.karpovich.security.jpa.repository.UserRepository;
import by.karpovich.security.mapping.JwtResponseMapper;
import by.karpovich.security.mapping.UserMapper;
import by.karpovich.security.security.JwtUtils;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private static final Long USER_ID = 1L;

    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private UserMapper userMapper;
    @Mock
    private JwtResponseMapper jwtResponseMapper;
    @InjectMocks
    private UserServiceImpl userService;

//    @Test
//    void findById() {
//        UserEntity userEntity = mock(UserEntity.class);
//        Optional<UserEntity> optionalUserEntity = Optional.of(userEntity);
//
//        when(userRepository.findById(USER_ID)).thenReturn(optionalUserEntity);
//
//        UserDtoFullOut userDto = mock(UserDtoFullOut.class);
//        when(userMapper.mapUserFullDtoFromEntity(any(UserEntity.class))).thenReturn(userDto);
//
//        Optional<UserDtoFullOut> result = userService.findById(USER_ID);
//
//        assertEquals(result, userDto);
//
//        verify(userRepository).findById(USER_ID);
//        verify(userMapper).mapUserFullDtoFromEntity(userEntity);
//    }
}