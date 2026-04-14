package com.juandelacierva.ChurnGym.domain;

import java.time.LocalDateTime;

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
@Table(name = "importaciones")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Importacion 
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_descriptivo")
    private String nombreDescriptivo;

    @Column(name = "fecha_importacion", nullable = false)
    private LocalDateTime fechaImportacion;

    @Column(name = "total_registros", nullable = false)
    private Integer totalRegistros = 0;

    @Column(name = "registros_ok", nullable = false)
    private Integer registrosOk = 0;

    @Column(name = "registros_error", nullable = false)
    private Integer registrosError = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoImportacion estado = EstadoImportacion.PROCESANDO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

}
