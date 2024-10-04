package com.example.cargobay.boundary.dtos;

import com.example.cargobay.utility.validation.ValidPassword;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class SignUpRequestDto {
    @NotBlank
    @Pattern(regexp = "[A-Za-z0-9.]+@\\w*\\.de")
    private String email;

    @NotBlank
    private String username;

    @ValidPassword
    private String password;
}
