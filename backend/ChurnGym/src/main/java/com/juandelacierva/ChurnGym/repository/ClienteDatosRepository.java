package com.juandelacierva.ChurnGym.repository;

import com.juandelacierva.ChurnGym.entity.ClienteDatos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteDatosRepository extends JpaRepository<ClienteDatos, Long> {
}
