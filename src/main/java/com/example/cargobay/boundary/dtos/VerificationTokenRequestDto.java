package com.example.cargobay.boundary.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class VerificationTokenRequestDto {
    @NotNull
    private String token;
}
