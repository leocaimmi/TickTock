package com.TickTock.TickTock.birthday.application.dtos.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BirthdayResponse {

    private Long id;
    private String name; // Nombre del cumplañero
    private LocalDate birthDate; // Fecha de nacimiento
    private Integer turningAge; // edad que cumple o tiene
    private Long userId; // FK del usuario dueño del recordatorio
    private String phrase; // Frase de cumpleaños
    private Boolean status; // Estado para recordar o no
}
