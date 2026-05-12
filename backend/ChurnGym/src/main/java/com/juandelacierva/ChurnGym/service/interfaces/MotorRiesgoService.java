package com.juandelacierva.ChurnGym.service.interfaces;

import com.juandelacierva.ChurnGym.domain.ClienteDatos;
import com.juandelacierva.ChurnGym.domain.NivelRiesgo;

public interface MotorRiesgoService {
    NivelRiesgo calcularNivel(ClienteDatos datos);
    Double      calcularProbabilidad(ClienteDatos datos);
    String      asignarGrupo(ClienteDatos datos);
}
