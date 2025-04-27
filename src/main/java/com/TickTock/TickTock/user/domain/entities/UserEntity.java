package com.TickTock.TickTock.user.domain.entities;

import com.TickTock.TickTock.birthday.domain.entities.BirthdayEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;


    private LocalDate bornDate;

    @Email
    private String email;

    private Boolean status;
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<BirthdayEntity> birthdayEntityList;

}
