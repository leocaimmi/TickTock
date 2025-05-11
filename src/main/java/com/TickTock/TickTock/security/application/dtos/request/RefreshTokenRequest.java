package com.TickTock.TickTock.security.application.dtos.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class RefreshTokenRequest {
    private String refreshToken;
}
