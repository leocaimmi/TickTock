package com.TickTock.TickTock.user.application.dtos.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @Schema(description = "Username of the user", example = "john_doe")
    private String username;

    @Schema(description = "Date of birth of the user", example = "1990-01-01")
    private LocalDate bornDate;

    @Schema(description = "Email of the user", example = "john_doe@example.com")
    @Email (message = "Email should be valid")
    private String email;
}
