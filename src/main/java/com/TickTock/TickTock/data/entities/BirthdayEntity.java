package com.TickTock.TickTock.data.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "birthdays")
public class BirthdayEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Nombre del cumplañero

    private LocalDate birthDate; // Fecha de nacimiento

    private Integer turningAge; // edad que cumple

    private Boolean status; // Estado para recordar o no

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // FK
    private UserEntity userEntity; // El usuario dueño del recordatorio
}
