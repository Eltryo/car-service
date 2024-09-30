package com.example.cargobay.unit;


import com.example.cargobay.boundary.dtos.SignUpRequestDto;
import com.example.cargobay.boundary.dtos.SignUpResponseDto;
import com.example.cargobay.boundary.mapper.SignUpDtoMapper;
import com.example.cargobay.entity.User;
import com.example.cargobay.repository.UserRepository;
import com.example.cargobay.service.impl.AuthServiceImpl;
import com.example.cargobay.utility.config.ApplicationUserRole;
import com.example.cargobay.utility.config.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.authentication.AuthenticationManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private SignUpDtoMapper signUpDtoMapper;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        authService = new AuthServiceImpl(userRepository, authenticationManager, tokenProvider, signUpDtoMapper);
    }

    @Test
    void signUpTest() {
        //given
        User user = new User("user", "Password1!", ApplicationUserRole.USER, "user@gmail.de");
        SignUpResponseDto dto = SignUpResponseDto.builder().username("user").email("user@gmail.de").build();

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(signUpDtoMapper.toSignUpRequestDto(Mockito.any(User.class))).thenReturn(dto);

        //when
        SignUpRequestDto david = SignUpRequestDto.builder()
                .email("user@gmail.com")
                .username("user")
                .password("Password1!")
                .role(ApplicationUserRole.USER)
                .build();
        SignUpResponseDto result = authService.signUp(david);

        //then
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
        Mockito.verify(signUpDtoMapper, Mockito.times(1)).toSignUpRequestDto(Mockito.any(User.class));

        assertThat(result).isEqualTo(dto);
    }
}
