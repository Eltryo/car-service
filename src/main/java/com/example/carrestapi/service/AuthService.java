package com.example.carrestapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.carrestapi.dtos.SignUpDto;
import com.example.carrestapi.entities.User;
import com.example.carrestapi.repository.UserRepository;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByLogin(username);
        return user;
    }

    public UserDetails signUp(SignUpDto data) throws Exception {
        var user = userRepository.findByLogin(data.getLogin());
        if(user != null) {
            throw new Exception("Username already exists");
        }

        var encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        var newUser = new User(data.getLogin(), encryptedPassword, data.getRole());

        return userRepository.save(newUser);
    }
}
