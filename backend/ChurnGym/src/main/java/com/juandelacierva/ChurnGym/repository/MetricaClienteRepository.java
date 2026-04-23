package com.juandelacierva.ChurnGym.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.juandelacierva.ChurnGym.domain.MetricaCliente;

public interface MetricaClienteRepository extends JpaRepository<MetricaCliente, Long>
{    
    // SELECT * FROM metrica_cliente WHERE importacion_id = ?
	List<MetricaCliente>        findByImportacion_Id(Long importacionId);
    // SELECT * FROM metrica_cliente WHERE cliente_id = ? AND importacion_id = ?
	Optional<MetricaCliente>    findByCliente_IdAndImportacion_Id(Long clienteId, Long importacionId);
}