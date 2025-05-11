package com.TickTock.TickTock.user.application.dtos.response;


import com.TickTock.TickTock.birthday.application.dtos.response.BirthdayResponse;
import com.TickTock.TickTock.shared.infrastructure.utils.Role;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private LocalDate bornDate;
    private Boolean status;
    private List<BirthdayResponse> birthdayResponseList;
    private Role role;


}
