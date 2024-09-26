package com.example.cargobay.boundary.controller.dtos;

import lombok.Data;

@Data
public class JwtDto {
    String accessToken;

    public JwtDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
