package com.juandelacierva.ChurnGym.repository;

import com.juandelacierva.ChurnGym.domain.ResultadoAnalisis;
import com.juandelacierva.ChurnGym.domain.enums.DeportePrincipal;
import com.juandelacierva.ChurnGym.domain.enums.FranjaHoraria;
import com.juandelacierva.ChurnGym.domain.enums.GrupoRiesgo;
import com.juandelacierva.ChurnGym.domain.enums.NivelRiesgo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface ResultadoAnalisisRepository extends JpaRepository<ResultadoAnalisis, Long>
{
    Optional<ResultadoAnalisis> findByClienteDatosId(Long id);

    @Query("SELECT r FROM ResultadoAnalisis r JOIN r.clienteDatos cd " +
           "WHERE (:nivelRiesgo IS NULL OR r.nivelRiesgo        = :nivelRiesgo) " +
           "AND   (:grupo       IS NULL OR r.grupo              = :grupo)       " +
           "AND   (:franja      IS NULL OR cd.franjaHoraria     = :franja)      " +
           "AND   (:deporte     IS NULL OR cd.deportePrincipal  = :deporte)     ")
    Page<ResultadoAnalisis> findWithFilters(
            @Param("nivelRiesgo") NivelRiesgo     nivelRiesgo,
            @Param("grupo")       GrupoRiesgo     grupo,
            @Param("franja")      FranjaHoraria   franja,
            @Param("deporte")     DeportePrincipal deporte,
            Pageable              pageable
    );
}
