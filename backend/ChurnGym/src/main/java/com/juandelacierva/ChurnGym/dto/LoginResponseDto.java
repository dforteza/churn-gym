package com.juandelacierva.ChurnGym.dto;

import lombok.Data;

@Data
public class LoginResponseDto 
{
    private String token;
    private String username;
    private String rol;
}
