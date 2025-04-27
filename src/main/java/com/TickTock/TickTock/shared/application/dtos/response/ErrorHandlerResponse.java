package com.TickTock.TickTock.shared.application.dtos.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ErrorHandlerResponse {
    private String error;
    private String message;
    private String summary;
    private LocalDateTime timestamp;

}