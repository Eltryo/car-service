package com.example.cargobay.service;

import com.example.cargobay.boundary.dtos.JwtDto;
import com.example.cargobay.boundary.dtos.SignInDto;
import com.example.cargobay.boundary.dtos.SignUpRequestDto;
import com.example.cargobay.boundary.dtos.SignUpResponseDto;

public interface AuthService {
    SignUpResponseDto signUp(SignUpRequestDto data);
    JwtDto signIn(SignInDto data);
}
