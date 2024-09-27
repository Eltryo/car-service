package com.example.cargobay.service;

import com.example.cargobay.utility.exceptions.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cargobay.boundary.dtos.SignUpRequestDto;
import com.example.cargobay.entity.User;
import com.example.cargobay.repository.UserRepository;

@Service
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByLogin(username);
        return user;
    }

    public void signUp(SignUpRequestDto data) {
        var user = userRepository.findByLogin(data.getUsername());
        if(user != null) {
            throw new AppException("Username already exists", HttpStatus.BAD_REQUEST);
        }

        var password = data.getPassword();
        var encryptedPassword = new BCryptPasswordEncoder().encode(password);

        var username = data.getUsername();
        var role = data.getRole();
        var newUser = new User(username, encryptedPassword, role);

        userRepository.save(newUser);
    }
}
