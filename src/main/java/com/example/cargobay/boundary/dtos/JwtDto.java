package com.example.cargobay.boundary.dtos;

import lombok.Data;

@Data
public class JwtDto {
    private String accessToken;

    public JwtDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
