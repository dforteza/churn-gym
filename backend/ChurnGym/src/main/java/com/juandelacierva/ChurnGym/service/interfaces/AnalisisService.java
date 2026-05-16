package com.juandelacierva.ChurnGym.service.interfaces;

import com.juandelacierva.ChurnGym.dto.AnalisisResumenResponseDto;
import com.juandelacierva.ChurnGym.dto.ClienteAnalisisResponseDto;
import com.juandelacierva.ChurnGym.domain.enums.DeportePrincipal;
import com.juandelacierva.ChurnGym.domain.enums.FranjaHoraria;
import com.juandelacierva.ChurnGym.domain.enums.GrupoRiesgo;
import com.juandelacierva.ChurnGym.domain.enums.NivelRiesgo;
import org.springframework.data.domain.Pageable;

public interface AnalisisService
{
    AnalisisResumenResponseDto getAnalisisVigente(NivelRiesgo nivelRiesgo, GrupoRiesgo grupo, FranjaHoraria franja, DeportePrincipal deporte, Pageable pageable);
    AnalisisResumenResponseDto lanzarAnalisis();
    ClienteAnalisisResponseDto getDetalleCliente(Long clienteId);
}
