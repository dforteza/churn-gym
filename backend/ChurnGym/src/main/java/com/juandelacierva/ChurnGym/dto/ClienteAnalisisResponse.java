package com.juandelacierva.ChurnGym.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ClienteAnalisisResponse 
{
    private Long    clienteId;
    private String  nombre;
    private String  apellidos;
    private String  email;
    private Double  frecuenciaSemanal;
    private Integer semanasInactivo;
    private Double  tendenciaMensual;
    private String  franjaHoraria;
    private String  deportePrincipal;
    private Integer mesesComoSocio;
    private String  nivelRiesgo;
    private Double  probabilidadAbandono;
    private String  grupo;
}
