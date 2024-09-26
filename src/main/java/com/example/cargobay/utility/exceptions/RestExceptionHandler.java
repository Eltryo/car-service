package com.example.cargobay.utility.exceptions;

import com.example.cargobay.dtos.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> handleAppException(AppException exception) {
        return ResponseEntity.status(exception.getStatus()).body(new ErrorResponseDto(exception.getMessage()));
    }
}
