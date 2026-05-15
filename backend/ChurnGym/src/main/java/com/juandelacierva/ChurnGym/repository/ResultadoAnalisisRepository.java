package com.juandelacierva.ChurnGym.repository;

import com.juandelacierva.ChurnGym.domain.ResultadoAnalisis;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ResultadoAnalisisRepository extends JpaRepository<ResultadoAnalisis, Long> 
{
    // SELECT * FROM resultados_analisis WHERE cliente_datos_id = ?
    Optional<ResultadoAnalisis> findByClienteDatosId(Long id);
}
