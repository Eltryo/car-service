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
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByLogin(username);
        return user;
    }

    public UserDetails signUp(SignUpRequestDto data) {
        var user = userRepository.findByLogin(data.getUsername());
        if(user != null) {
            throw new AppException("Username already exists", HttpStatus.BAD_REQUEST);
        }

        var encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        var newUser = new User(data.getUsername(), encryptedPassword, data.getRole());

        return userRepository.save(newUser);
    }
}
