package com.juandelacierva.ChurnGym.service.interfaces;

import com.juandelacierva.ChurnGym.entity.ClienteDatos;
import com.juandelacierva.ChurnGym.entity.NivelRiesgo;

public interface MotorRiesgoService {
    NivelRiesgo calcularNivel(ClienteDatos datos);
    Double      calcularProbabilidad(ClienteDatos datos);
    String      asignarGrupo(ClienteDatos datos);
}
