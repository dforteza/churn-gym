package com.juandelacierva.ChurnGym.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class PerfilUpdateDto {

    @Size(max = 150)
    private String nombreGimnasio;

    @Size(max = 200)
    private String direccion1;

    @Size(max = 100)
    private String direccion2;

    @Pattern(
        regexp = "^\\+?[0-9\\s\\-().]{6,20}$",
        message = "Formato de teléfono no válido"
    )
    private String telefono;
}
