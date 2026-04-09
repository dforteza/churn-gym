package com.juandelacierva.ChurnGym.dto;

import lombok.Data;

@Data
public class MetricaClienteResponseDto 
{
    private Long    clienteId;
    private String  nombre;
    private String  apellidos;
    private String  email;
    private Double  frecuenciaSemanal;
    private Integer semanasInactivo;
    private Double  tendenciaMensual;
    private String  nivelRiesgo;
}
