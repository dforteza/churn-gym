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
        regexp = "^(\\+34[\\s\\-]?)?[6-9][0-9]{2}[\\s\\-]?[0-9]{3}[\\s\\-]?[0-9]{3}$",
        message = "Debe ser un número español válido (ej. 910 123 456 o +34 910 123 456)"
    )
    private String telefono;
}
