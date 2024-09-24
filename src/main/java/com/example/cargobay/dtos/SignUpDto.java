package com.example.cargobay.dtos;

import com.example.cargobay.security.ApplicationUserRole;

import lombok.Data;

@Data
public class SignUpDto{
    private String login;
    private String password;
    private ApplicationUserRole role;
}
