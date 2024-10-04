package com.example.cargobay.unit;


import com.example.cargobay.boundary.dtos.SignUpRequestDto;
import com.example.cargobay.boundary.dtos.SignUpResponseDto;
import com.example.cargobay.boundary.mapper.SignUpDtoMapper;
import com.example.cargobay.entity.Role;
import com.example.cargobay.entity.User;
import com.example.cargobay.repository.RoleRepository;
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
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.authentication.AuthenticationManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private SignUpDtoMapper signUpDtoMapper;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private RoleRepository roleRepository;

    private AuthServiceImpl authService;

    @BeforeEach
    public void setUp() {
        authService = new AuthServiceImpl(userRepository, authenticationManager, tokenProvider, signUpDtoMapper, roleRepository);
    }

    @Test
    public void signUpTest() {
        //given
        Role role = new Role(ApplicationUserRole.USER);
        User user = new User("user", "Password1!", role, "user@gmail.de");
        SignUpResponseDto dto = SignUpResponseDto.builder()
                .username("user")
                .email("user@gmail.de")
                .role(ApplicationUserRole.USER)
                .build();

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(roleRepository.findByName(Mockito.any(ApplicationUserRole.class))).thenReturn(role);
        Mockito.when(signUpDtoMapper.toSignUpRequestDto(Mockito.any(User.class))).thenReturn(dto);

        //when
        SignUpRequestDto david = SignUpRequestDto.builder()
                .email("user@gmail.com")
                .username("user")
                .password("Password1!")
                .build();
        SignUpResponseDto result = authService.signUp(david);

        //then
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
        Mockito.verify(signUpDtoMapper, Mockito.times(1)).toSignUpRequestDto(Mockito.any(User.class));
        Mockito.verify(roleRepository, Mockito.times(1)).findByName(Mockito.any(ApplicationUserRole.class));

        assertThat(result).isEqualTo(dto);
    }
}
