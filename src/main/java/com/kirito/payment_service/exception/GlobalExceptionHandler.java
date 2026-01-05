package com.kirito.payment_service.exception;

import com.kirito.payment_service.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleAccountNotFound(AccountNotFoundException e) {
        ErrorResponseDTO error = new ErrorResponseDTO(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponseDTO> handleInsufficientBalance(InsufficientBalanceException e) {
        ErrorResponseDTO error = new ErrorResponseDTO(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception e) {
        ErrorResponseDTO error = new ErrorResponseDTO("Internal Server Error: " + e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
