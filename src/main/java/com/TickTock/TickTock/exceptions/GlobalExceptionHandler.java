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
        return buildResponse(HttpStatus.BAD_REQUEST, "Illegal State", ex.getMessage(), null);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorHandlerResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "Resource not found", "The resource could not be found in the system.", ex.getMessage());
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ErrorHandlerResponse> handleEntityExistsException(EntityExistsException ex) {
        return buildResponse(HttpStatus.CONFLICT, "Entity already exists", ex.getMessage(), null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorHandlerResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Illegal Argument", ex.getMessage(), null);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorHandlerResponse> handleNullPointerException(NullPointerException ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "Surely something went wrong", ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorHandlerResponse> handleUnauthorizedException(AccessDeniedException ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "Denied access", ex.getMessage(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorHandlerResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return buildResponse(HttpStatus.BAD_REQUEST, "Validation Error", "Invalid request body", errors);
    }

    private ResponseEntity<ErrorHandlerResponse> buildResponse(HttpStatus status, String error, String message, String summary) {
        return ResponseEntity.status(status)
                .body(ErrorHandlerResponse.builder()
                        .error(error)
                        .message(message)
                        .summary(summary)
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}
