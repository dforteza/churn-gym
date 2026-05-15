package com.juandelacierva.ChurnGym.service;

import com.juandelacierva.ChurnGym.domain.ClienteDatos;
import com.juandelacierva.ChurnGym.domain.enums.NivelRiesgo;
import com.juandelacierva.ChurnGym.service.interfaces.MotorRiesgoService;

import org.springframework.stereotype.Service;

/**
 * Motor de Reglas de Riesgo (Java)
 * ¡¡¡ las reglas determinan el RIESGO, el clustering determina el GRUPO !!! .
 */
@Service
public class MotorRiesgoServiceImpl implements MotorRiesgoService 
{
    // Umbrales de clasificación sobre la probabilidad calculada (0.0 – 1.0)
    private static final double UMBRAL_ALTO  = 0.65;
    private static final double UMBRAL_MEDIO = 0.30;

    @Override
    public Double calcularProbabilidad(ClienteDatos clienteDatos) 
    {
        return (0.0);
    }

    /**
     * Clasifica el nivel de riesgo derivándolo de la probabilidad calculada.
     *   - ALTO  : prob ≥ 0.65
     *   - MEDIO : prob ≥ 0.30
     *   - BAJO  : prob < 0.30
     */
    @Override
    public NivelRiesgo calcularNivel(ClienteDatos clienteDatos) 
    {
        NivelRiesgo nivel;
        double      prob;

        prob = calcularProbabilidad(clienteDatos);

        if (prob >= UMBRAL_ALTO)
            nivel = NivelRiesgo.ALTO;
        else if (prob >= UMBRAL_MEDIO)
            nivel = NivelRiesgo.MEDIO;
        else
            nivel = NivelRiesgo.BAJO;

        return (nivel);
    }

    /**
     * PLACEHOLDER — será reemplazado en Bloque 3 por una llamada HTTP
     * al microservicio Python (KMeans clustering).
     *
     * Segmentación provisional basada en reglas de negocio:
     *   G1 CONSOLIDADO_EN_RIESGO : socio antiguo (>12 meses) con ≥3 semanas de ausencia
     *   G2 NUEVO_SIN_ENGANCHE    : socio nuevo (≤3 meses) con frecuencia < 1 vez/semana
     *   G3 ACTIVO_ESTABLE        : asistencia regular (≥2/sem) sin ausencias recientes
     *   G4 IRREGULAR             : red de seguridad para perfiles no clasificables
     */
    @Override
    public String asignarGrupo(ClienteDatos clienteDatos) 
    {
        String grupo;

        int    semanas    = clienteDatos.getSemanasInactivo() != null ? clienteDatos.getSemanasInactivo() : 0;
        int    meses      = clienteDatos.getMesesComoSocio()  != null ? clienteDatos.getMesesComoSocio()  : 0;
        double frecuencia = clienteDatos.getFrecuenciaSemanal() != null ? clienteDatos.getFrecuenciaSemanal() : 0.0;

        if (meses > 12 && semanas >= 3)
        {
            // G1: socio consolidado que ha dejado de venir — máxima prioridad de retención
            grupo = "CONSOLIDADO_EN_RIESGO";
        }
        else if (meses <= 3 && frecuencia < 1.0)
        {
            // G2: socio nuevo sin enganche — foco en estrategias de bienvenida y fidelización
            grupo = "NUEVO_SIN_ENGANCHE";
        }
        else if (frecuencia >= 2.0 && semanas == 0)
        {
            // G3: socio activo y sin ausencias recientes — perfil estable y comprometido
            grupo = "ACTIVO_ESTABLE";
        }   
        else
            grupo = "IRREGULAR";
        
        return (grupo);
    }
}
