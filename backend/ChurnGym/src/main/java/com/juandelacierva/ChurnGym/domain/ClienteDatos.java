package com.juandelacierva.ChurnGym.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clientes_datos")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ClienteDatos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private ClientePrivado clientePrivado;

    private Integer anyoNacimiento;
    private Integer mesesComoSocio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FranjaHoraria franjaHoraria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeportePrincipal deportePrincipal;

    private Double frecuenciaSemanal;
    private Integer semanasInactivo;
    private Double tendenciaMensual;

    @OneToOne(mappedBy = "clienteDatos", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ResultadoAnalisis resultadoAnalisis;
}
