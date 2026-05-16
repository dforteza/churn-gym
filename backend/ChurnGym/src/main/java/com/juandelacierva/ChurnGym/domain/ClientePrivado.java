package com.juandelacierva.ChurnGym.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clientes_privados")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ClientePrivado 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellidos;

    @Column(unique = true, nullable = false)
    private String dni;

    @Column(name = "gmail", unique = true, nullable = false)
    private String email;

    @OneToOne(mappedBy = "clientePrivado", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ClienteDatos clienteDatos;
}
