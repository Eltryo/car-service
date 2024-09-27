package com.example.cargobay.boundary.dtos;

import com.example.cargobay.utility.config.ApplicationUserRole;

import com.example.cargobay.utility.validation.ValidPassword;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class SignUpRequestDto {
    @Pattern(regexp = "[A-Za-z0-9.]+@th-nuernberg\\.de")
    private String email;

    @NotBlank
    private String username;

    @ValidPassword
    private String password;

    private ApplicationUserRole role;
}
