package com.juandelacierva.ChurnGym.repository;

import com.juandelacierva.ChurnGym.entity.ResultadoAnalisis;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ResultadoAnalisisRepository extends JpaRepository<ResultadoAnalisis, Long> 
{
    Optional<ResultadoAnalisis> findByClienteDatosId(Long id);
}
