package com.example.cargobay.boundary.dtos;

import com.example.cargobay.utility.config.ApplicationUserRole;

import lombok.Data;

@Data
public class SignUpDto{
    private String login;
    private String password;
    private ApplicationUserRole role;
}
