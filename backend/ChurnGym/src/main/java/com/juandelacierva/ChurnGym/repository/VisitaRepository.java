package com.juandelacierva.ChurnGym.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juandelacierva.ChurnGym.domain.Visita;

public interface VisitaRepository extends JpaRepository<Visita, Long>
{
    // SELECT * FROM visita WHERE importacion_id = ?
	List<Visita> findByImportacion_Id(Long importacionId);

    // SELECT * FROM visita WHERE cliente_id = ? AND importacion_id = ?
	List<Visita> findByCliente_IdAndImportacion_Id(Long clienteId, Long importacionId);
}
