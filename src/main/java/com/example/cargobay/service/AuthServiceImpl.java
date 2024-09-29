package com.example.cargobay.service;

import com.example.cargobay.boundary.dtos.JwtDto;
import com.example.cargobay.boundary.dtos.SignInDto;
import com.example.cargobay.boundary.dtos.SignUpResponseDto;
import com.example.cargobay.boundary.mapper.SignUpDtoMapper;
import com.example.cargobay.utility.config.TokenProvider;
import com.example.cargobay.utility.exceptions.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cargobay.boundary.dtos.SignUpRequestDto;
import com.example.cargobay.entity.User;
import com.example.cargobay.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements UserDetailsService, AuthService{
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final SignUpDtoMapper signUpDtoMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username);
    }

    public SignUpResponseDto signUp(SignUpRequestDto data) {
        var user = userRepository.findByLogin(data.getUsername());
        if (user != null) {
            throw new AppException("Username already exists", HttpStatus.BAD_REQUEST);
        }

        var password = data.getPassword();
        var encryptedPassword = new BCryptPasswordEncoder().encode(password);

        var username = data.getUsername();
        var role = data.getRole();
        var email = data.getEmail();
        var newUser = new User(username, encryptedPassword, role, email);

        var savedUser = userRepository.save(newUser);
        return signUpDtoMapper.toSignUpRequestDto(savedUser);
    }

    public JwtDto signIn(SignInDto data) {
        String username = data.getUsername();
        String password = data.getPassword();

        var usernamePassword = new UsernamePasswordAuthenticationToken(username, password);
        var authUser = authenticationManager.authenticate(usernamePassword);
        var accessToken = tokenProvider.generateAccessToken((User) authUser.getPrincipal());

        return new JwtDto(accessToken);
    }
}
