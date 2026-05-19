package com.juandelacierva.ChurnGym.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class PerfilUpdateDto {

    private String nombreGimnasio;
    private String direccion1;
    private String direccion2;
    private String telefono;
}
