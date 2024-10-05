package com.example.cargobay.control.service.impl;

import com.example.cargobay.boundary.dtos.JwtDto;
import com.example.cargobay.boundary.dtos.SignInDto;
import com.example.cargobay.boundary.dtos.SignUpRequestDto;
import com.example.cargobay.boundary.dtos.SignUpResponseDto;
import com.example.cargobay.boundary.mapper.SignUpDtoMapper;
import com.example.cargobay.control.events.OnRegistrationCompleteEvent;
import com.example.cargobay.control.service.AuthService;
import com.example.cargobay.control.service.VerificationService;
import com.example.cargobay.entity.User;
import com.example.cargobay.repository.RoleRepository;
import com.example.cargobay.repository.UserRepository;
import com.example.cargobay.repository.VerificationTokenRepository;
import com.example.cargobay.utility.config.ApplicationUserRole;
import com.example.cargobay.utility.config.TokenProvider;
import com.example.cargobay.utility.exceptions.AppException;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private TokenProvider tokenProvider;
    private SignUpDtoMapper signUpDtoMapper;
    private RoleRepository roleRepository;
    private ApplicationEventPublisher eventPublisher;
    private VerificationTokenRepository verificationTokenRepository;
    private VerificationService verificationService;

    public SignUpResponseDto signUp(SignUpRequestDto data) {
        var user = userRepository.findByLogin(data.getUsername());
        if (user.isPresent()) {
            throw new AppException("Username already exists", HttpStatus.BAD_REQUEST);
        }

        var password = data.getPassword();
        var encryptedPassword = new BCryptPasswordEncoder().encode(password);

        var username = data.getUsername();
        var email = data.getEmail();
        var role = roleRepository.findByName(ApplicationUserRole.USER);

        var newUser = User.builder()
                .login(username)
                .password(encryptedPassword)
                .role(role)
                .email(email)
                .enabled(false)
                .build();
        var savedUser = userRepository.save(newUser);

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(newUser));

        return signUpDtoMapper.toSignUpRequestDto(savedUser);
    }

    public JwtDto signIn(SignInDto data) {
        String username = data.getUsername();
        UserDetails user = userRepository.findByLogin(username).orElseThrow(() -> new AppException("User does not exist", HttpStatus.NOT_FOUND));
        if (!user.isEnabled()) {
            throw new AppException("User is not enabled", HttpStatus.LOCKED);
        }

        String password = data.getPassword();
        var authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        var authUser = authenticationManager.authenticate(authenticationToken);
        var accessToken = tokenProvider.generateAccessToken((User) authUser.getPrincipal());

        return new JwtDto(accessToken);
    }

    public void confirmRegistration(String token) {
        var verificationToken = verificationService.validateVerificationToken(token);

        User user = verificationToken.getUser();
        long expirationTime = verificationToken.getExpiryDate().getTime();
        if (expirationTime < System.currentTimeMillis()) {
            userRepository.delete(user);
            throw new AppException("Verification Code is expired, please sign up again", HttpStatus.BAD_REQUEST);
        }

        user.setEnabled(true);
        userRepository.save(user);
    }
}
