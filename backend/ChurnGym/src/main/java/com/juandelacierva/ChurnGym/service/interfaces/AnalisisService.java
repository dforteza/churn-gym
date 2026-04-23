package com.juandelacierva.ChurnGym.service.interfaces;

import com.juandelacierva.ChurnGym.dto.AnalisisResumenResponse;
import com.juandelacierva.ChurnGym.dto.ClienteAnalisisResponse;

public interface AnalisisService 
{
    AnalisisResumenResponse getAnalisisVigente();
    AnalisisResumenResponse lanzarAnalisis();
    ClienteAnalisisResponse getDetalleCliente(Long clienteId);
}
