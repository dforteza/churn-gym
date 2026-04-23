package com.juandelacierva.ChurnGym.service;

import com.juandelacierva.ChurnGym.entity.ClienteDatos;
import com.juandelacierva.ChurnGym.entity.NivelRiesgo;
import com.juandelacierva.ChurnGym.service.interfaces.MotorRiesgoService;

import org.springframework.stereotype.Service;

@Service
public class MotorRiesgoServiceImpl implements MotorRiesgoService {

    @Override
    public NivelRiesgo calcularNivel(ClienteDatos datos) {
        return NivelRiesgo.BAJO;
    }

    @Override
    public Double calcularProbabilidad(ClienteDatos datos) {
        return 0.0;
    }

    @Override
    public String asignarGrupo(ClienteDatos datos) {
        return "SIN_CLASIFICAR";
    }
}
