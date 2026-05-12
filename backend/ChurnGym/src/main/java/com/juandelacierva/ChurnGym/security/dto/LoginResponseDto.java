package com.juandelacierva.ChurnGym.security.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class LoginResponseDto 
{
    private String token;
    private String username;
    private String rol;
}
