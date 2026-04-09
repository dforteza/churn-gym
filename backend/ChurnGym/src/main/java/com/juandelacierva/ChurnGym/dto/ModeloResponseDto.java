package com.juandelacierva.ChurnGym.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ModeloResponseDto 
{
    private Long          id;
    private String        nombreDescriptivo;
    private LocalDateTime fechaImportacion;
    private Integer       totalClientes;
    private Integer       alto;
    private Integer       medio;
    private Integer       bajo;
    private String        estado;
}
