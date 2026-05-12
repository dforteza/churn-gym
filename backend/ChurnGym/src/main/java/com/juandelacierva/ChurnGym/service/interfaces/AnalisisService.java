package com.juandelacierva.ChurnGym.service.interfaces;

import com.juandelacierva.ChurnGym.dto.AnalisisResumenResponseDto;
import com.juandelacierva.ChurnGym.dto.ClienteAnalisisResponseDto;

public interface AnalisisService 
{
    AnalisisResumenResponseDto getAnalisisVigente();
    AnalisisResumenResponseDto lanzarAnalisis();
    ClienteAnalisisResponseDto getDetalleCliente(Long clienteId);
}
