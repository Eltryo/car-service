package com.example.cargobay.boundary.dtos;

import com.example.cargobay.utility.validation.ValidPassword;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class SignInDto {
    @NotBlank
    private final String username;

    @ValidPassword
    private final String password;
}
