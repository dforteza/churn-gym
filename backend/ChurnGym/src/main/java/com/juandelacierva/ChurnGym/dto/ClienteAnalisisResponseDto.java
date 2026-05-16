package com.juandelacierva.ChurnGym.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ClienteAnalisisResponseDto 
{
    private Long    clienteId;
    // Datos privados
    private String  nombre;
    private String  apellidos;
    private String  email;
    // Datos de análisis
    private Double  frecuenciaSemanal;
    private Integer semanasInactivo;
    private Double  tendenciaMensual;
    private String  franjaHoraria;
    private String  deportePrincipal;
    private Integer mesesComoSocio;
    // Resultado del análisis
    private String  nivelRiesgo;
    private Double  probabilidadAbandono;
    private String  grupo;
}
