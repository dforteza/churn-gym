package com.juandelacierva.ChurnGym.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class AnalisisResumenResponseDto 
{
    private LocalDateTime                   calculadoEn;
    private Integer                         totalClientes;
    private Integer                         alto;
    private Integer                         medio;
    private Integer                         bajo;
    private List<ClienteAnalisisResponseDto>   resultados;
}
