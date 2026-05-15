package com.juandelacierva.ChurnGym.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import com.juandelacierva.ChurnGym.domain.enums.NivelRiesgo;

@Entity
@Table(name = "resultados_analisis")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ResultadoAnalisis 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_datos_id", nullable = false)
    private ClienteDatos clienteDatos;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NivelRiesgo nivelRiesgo;

    @Column(nullable = false)
    private Double probabilidadAbandono;

    @Column(nullable = false)
    private String grupo;

    @Column(nullable = false)
    private LocalDateTime calculadoEn;
}
