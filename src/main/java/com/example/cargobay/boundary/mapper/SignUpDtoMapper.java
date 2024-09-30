package com.example.cargobay.boundary.mapper;

import com.example.cargobay.boundary.dtos.SignUpResponseDto;
import org.mapstruct.Mapper;
import org.springframework.security.core.userdetails.UserDetails;

@Mapper(componentModel = "spring")
public interface SignUpDtoMapper {
    SignUpResponseDto toSignUpRequestDto(UserDetails user);
}
