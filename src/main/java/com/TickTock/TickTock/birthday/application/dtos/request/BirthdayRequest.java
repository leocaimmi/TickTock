package com.TickTock.TickTock.birthday.application.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BirthdayRequest {
    @Schema(description = "name", example = "john_doe")
    private String name; // Nombre del cumplañero

    @Schema(description = "Date of birth in ISO format (yyyy-MM-dd)", example = "2000-01-01")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate bornDate; // Fecha de nacimiento

    @Schema(description = "user id of the owner", example = "1")
    private Long userId; // FK del usuario dueño del recordatorio
}
