package com.example.cargobay.boundary.controller;

import com.example.cargobay.boundary.dtos.JwtDto;
import com.example.cargobay.boundary.dtos.MessageResponseDto;
import com.example.cargobay.boundary.dtos.SignInDto;
import com.example.cargobay.boundary.dtos.SignUpRequestDto;
import com.example.cargobay.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<MessageResponseDto> signUp(@RequestBody @Valid SignUpRequestDto data){
        authService.signUp(data);
        var responseMessage = new MessageResponseDto("Sign up successful");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseMessage);
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtDto> signIn(@RequestBody @Valid SignInDto data) {
        var token = authService.signIn(data);

        return ResponseEntity.ok(token);
    }
}
