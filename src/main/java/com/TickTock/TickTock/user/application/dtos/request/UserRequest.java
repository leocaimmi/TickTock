package com.TickTock.TickTock.user.application.dtos.request;

import com.TickTock.TickTock.shared.infrastructure.utils.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres")
    @Schema(description = "Username del usuario", example = "john_doe")
    private String username;

    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    @Schema(description = "Fecha de nacimiento del usuario", example = "1990-01-01")
    private LocalDate bornDate;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email debe ser válido")
    @Schema(description = "Email del usuario", example = "john_doe@example.com")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    @Schema(description = "Contraseña del usuario", example = "password123", minLength = 6)
    private String password;

    @Schema(description = "Rol del usuario", example = "USER", hidden = true)
    private Role role;
}