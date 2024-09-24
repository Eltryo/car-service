package com.example.cargobay.dtos;

import lombok.Data;

@Data
public class JwtDto {
    String accessToken;

    public JwtDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
