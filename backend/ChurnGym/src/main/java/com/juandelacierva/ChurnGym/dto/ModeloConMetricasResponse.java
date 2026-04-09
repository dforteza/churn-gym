package com.juandelacierva.ChurnGym.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class ModeloConMetricasResponse 
{
    private Long                            id;
    private String                          nombreDescriptivo;
    private LocalDateTime                   fechaImportacion;
    private Integer                         totalClientes;
    private Integer                         alto;
    private Integer                         medio;
    private Integer                         bajo;
    private String                          estado;
    private List<MetricaClienteResponseDto> metricas;
}
