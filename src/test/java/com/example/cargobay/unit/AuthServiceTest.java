package com.example.cargobay.unit;


import com.example.cargobay.boundary.dtos.SignUpRequestDto;
import com.example.cargobay.entity.Car;
import com.example.cargobay.entity.User;
import com.example.cargobay.repository.UserRepository;
import com.example.cargobay.service.AuthService;
import com.example.cargobay.service.CarService;
import com.example.cargobay.utility.config.ApplicationUserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService(userRepository);
    }

    @Test
    void signUpTest(){
        //given
        User user = new User("david", "Password1!", ApplicationUserRole.USER);
        Mockito.when(userRepository
                .save(Mockito.any(User.class)))
                .thenReturn(user);

        //when
        SignUpRequestDto david = SignUpRequestDto.builder()
                .email("david@gmail.com")
                .username("david")
                .password("Password1!")
                .build();
        UserDetails result = authService.signUp(david);

        //then
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
        assertThat(result).isEqualTo(user);
    }
}
