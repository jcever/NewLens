package com.newlens.backend.exception;

import com.newlens.backend.dto.ApiError;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations().stream()
                .findFirst()
                .map(ConstraintViolation::getMessage)
                .orElse("Validation failed");
        return ResponseEntity.badRequest()
                .body(new ApiError("validation_error", message));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiError> handleMissingParam(MissingServletRequestParameterException ex) {
        String message = "Required parameter '" + ex.getParameterName() + "' is missing";
        return ResponseEntity.badRequest()
                .body(new ApiError("missing_parameter", message));
    }
}
