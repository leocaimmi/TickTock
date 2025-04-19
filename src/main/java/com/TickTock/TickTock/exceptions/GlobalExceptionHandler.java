package com.TickTock.TickTock.exceptions;

import com.TickTock.TickTock.data.dtos.response.ErrorHandlerResponse;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Hidden // swagger anotation to hide this class from the documentation
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorHandlerResponse> handleIllegalStateException(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorHandlerResponse.builder()
                        .error("Illegal State")
                        .message(ex.getMessage())
                        .summary(null)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorHandlerResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorHandlerResponse.builder()
                        .error("Entity not found")
                        .message(ex.getMessage())
                        .summary(null)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ErrorHandlerResponse> handleEntityExistsException(EntityExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorHandlerResponse.builder()
                        .error("Entity already exists")
                        .message(ex.getMessage())
                        .summary(null)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorHandlerResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorHandlerResponse.builder()
                        .error("Illegal Argument")
                        .message(ex.getMessage())
                        .summary(null)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorHandlerResponse> handleNullPointerException(NullPointerException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorHandlerResponse.builder()
                        .error("Internal Server Error")
                        .message("Surely something went wrong")
                        .summary(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorHandlerResponse> handleUnauthorizedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorHandlerResponse.builder()
                        .error("Denied access")
                        .message(ex.getMessage())
                        .summary(null)
                        .timestamp(LocalDateTime.now())
                        .build());
    }
    //this is for validation errors @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorHandlerResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorHandlerResponse.builder()
                        .error("Validation Error")
                        .message("Invalid request body")
                        .summary(errors)
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}
