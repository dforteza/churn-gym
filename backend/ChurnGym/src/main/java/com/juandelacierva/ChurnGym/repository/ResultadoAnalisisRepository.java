package com.juandelacierva.ChurnGym.repository;

import com.juandelacierva.ChurnGym.domain.ResultadoAnalisis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface ResultadoAnalisisRepository extends JpaRepository<ResultadoAnalisis, Long>
{
    Optional<ResultadoAnalisis> findByClienteDatosId(Long id);

    @Query(value =
        "SELECT r.* FROM resultados_analisis r " +
        "JOIN clientes_datos cd ON r.cliente_datos_id = cd.id " +
        "JOIN clientes_privados cp ON cd.cliente_privado_id = cp.id " +
        "WHERE (:nivelRiesgo IS NULL OR r.nivel_riesgo         = :nivelRiesgo) " +
        "AND   (:grupo       IS NULL OR r.grupo                = :grupo)       " +
        "AND   (:franja      IS NULL OR cd.franja_horaria      = :franja)      " +
        "AND   (:deporte     IS NULL OR cd.deporte_principal   = :deporte)     " +
        "AND   unaccent(lower(cp.nombre || ' ' || cp.apellidos)) LIKE unaccent(lower(:nombreLike)) " +
        "ORDER BY r.probabilidad_abandono DESC",
    countQuery =
        "SELECT COUNT(*) FROM resultados_analisis r " +
        "JOIN clientes_datos cd ON r.cliente_datos_id = cd.id " +
        "JOIN clientes_privados cp ON cd.cliente_privado_id = cp.id " +
        "WHERE (:nivelRiesgo IS NULL OR r.nivel_riesgo         = :nivelRiesgo) " +
        "AND   (:grupo       IS NULL OR r.grupo                = :grupo)       " +
        "AND   (:franja      IS NULL OR cd.franja_horaria      = :franja)      " +
        "AND   (:deporte     IS NULL OR cd.deporte_principal   = :deporte)     " +
        "AND   unaccent(lower(cp.nombre || ' ' || cp.apellidos)) LIKE unaccent(lower(:nombreLike))",
    nativeQuery = true)
    Page<ResultadoAnalisis> findWithFilters(
            @Param("nivelRiesgo") String   nivelRiesgo,
            @Param("grupo")       String   grupo,
            @Param("franja")      String   franja,
            @Param("deporte")     String   deporte,
            @Param("nombreLike")  String   nombreLike,
            Pageable              pageable
    );
}
