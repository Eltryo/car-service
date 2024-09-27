package com.example.cargobay.utility.exceptions;

import com.example.cargobay.boundary.dtos.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> handleAppException(AppException exception) {
        var status = exception.getStatus();
        var message = exception.getMessage();

        return ResponseEntity
                .status(status)
                .body(new ErrorResponseDto(message));
    }
}
