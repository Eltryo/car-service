package com.example.cargobay.boundary.dtos;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class SignUpResponseDto {
    @NotBlank
    @Pattern(regexp = "[A-Za-z0-9.]+@\\w*\\.de")
    private String email;

    @NotBlank
    private String username;
}
