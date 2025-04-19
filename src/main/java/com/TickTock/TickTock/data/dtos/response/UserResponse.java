package com.TickTock.TickTock.data.dtos.response;


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


}
