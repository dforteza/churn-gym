package com.juandelacierva.ChurnGym.dto;

import lombok.*;
import org.springframework.data.domain.Page;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class AnalisisResumenResponseDto
{
    private LocalDateTime                       calculadoEn;

    private Integer                             totalClientes;
    private Integer                             alto;
    private Integer                             medio;
    private Integer                             bajo;

    private Page<ClienteAnalisisResponseDto>    resultados;
}
