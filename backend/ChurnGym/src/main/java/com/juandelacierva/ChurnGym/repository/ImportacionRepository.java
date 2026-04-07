package com.juandelacierva.ChurnGym.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juandelacierva.ChurnGym.domain.Importacion;

public interface ImportacionRepository extends JpaRepository<Importacion, Long>
{
    // SELECT * FROM importacion WHERE usuario_id = ? ORDER BY fecha_importacion DESC
	List<Importacion> findByUsuario_IdOrderByFechaImportacionDesc(Long usuarioId);
}
