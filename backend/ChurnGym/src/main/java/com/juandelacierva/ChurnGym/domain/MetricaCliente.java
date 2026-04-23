package com.juandelacierva.ChurnGym.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "metricas_clientes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MetricaCliente 
{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "frecuencia_semanal", nullable = false)
    private Double frecuenciaSemanal;

    @Column(name = "semanas_inactivo", nullable = false)
    private Integer semanasInactivo;

    @Column(name = "tendencia_mensual", nullable = false)
    private Double tendenciaMensual;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_riesgo", nullable = false)
    private NivelRiesgo nivelRiesgo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "importacion_id", nullable = false)
    private Importacion importacion;
}
