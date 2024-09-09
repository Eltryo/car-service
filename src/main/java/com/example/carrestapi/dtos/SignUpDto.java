package com.example.carrestapi.dtos;

import com.example.carrestapi.security.ApplicationUserRole;

import lombok.Data;

@Data
public class SignUpDto{
    private String login;
    private String password;
    private ApplicationUserRole role;
}
