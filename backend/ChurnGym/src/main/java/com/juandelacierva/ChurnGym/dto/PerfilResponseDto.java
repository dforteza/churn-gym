package com.juandelacierva.ChurnGym.dto;

import java.time.LocalDateTime;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class PerfilResponseDto 
{
    private String        username;
    private String        email;
    private String        nombreGimnasio;
    private String        direccion1;
    private String        direccion2;
    private String        telefono;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
