package com.juandelacierva.ChurnGym.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "resultados_analisis")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ResultadoAnalisis {

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

    private String grupo;

    @Column(nullable = false)
    private LocalDateTime calculadoEn;
}
