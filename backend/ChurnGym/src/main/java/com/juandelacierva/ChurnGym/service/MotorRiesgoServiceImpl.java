package com.juandelacierva.ChurnGym.service;

import com.juandelacierva.ChurnGym.domain.ClienteDatos;
import com.juandelacierva.ChurnGym.domain.enums.GrupoRiesgo;
import com.juandelacierva.ChurnGym.domain.enums.NivelRiesgo;
import com.juandelacierva.ChurnGym.service.interfaces.MotorRiesgoService;

import org.springframework.stereotype.Service;

@Service
public class MotorRiesgoServiceImpl implements MotorRiesgoService
{
    // ── Umbrales de clasificación (prob en [0.0, 1.0]) ────────────────────────
    private static final double UMBRAL_ALTO  = 0.65;
    private static final double UMBRAL_MEDIO = 0.30;

    // ── Pesos del modelo — calibrados con Nelder-Mead (calibrar_pesos.py) ─────
    private static final double BIAS         = -1.95;
    private static final double W_SEMANAS    =  4.61;
    private static final double W_FRECUENCIA =  0.34;
    private static final double W_TENDENCIA  =  1.53;
    private static final double W_ANTIGUEDAD = -1.13;

    // ── Límites de normalización al rango [0, 1] ──────────────────────────────
    private static final double MAX_SEMANAS    = 12.0;
    private static final double MAX_FRECUENCIA =  7.0;
    private static final double MAX_MESES      = 60.0;

    // ── Umbrales de reglas de segmentación de grupo ───────────────────────────
    private static final int    MESES_CONSOLIDADO = 12;
    private static final int    SEMANAS_AUSENCIA  =  3;
    private static final int    MESES_NUEVO       =  3;
    private static final double FREC_MIN_ENGANCHE =  1.0;
    private static final double FREC_MIN_ESTABLE  =  2.0;
    private static final int    MESES_MIN_PATRON  =  4;

    @Override
    public Double calcularProbabilidad(ClienteDatos cd)
    {
        double semanas    = Math.min(cd.getSemanasInactivo()   != null ? cd.getSemanasInactivo()   : 0, MAX_SEMANAS)    / MAX_SEMANAS;
        double frecuencia = Math.min(cd.getFrecuenciaSemanal() != null ? cd.getFrecuenciaSemanal() : 0, MAX_FRECUENCIA) / MAX_FRECUENCIA;
        double tendencia  = cd.getTendenciaMensual() != null ? cd.getTendenciaMensual() : 0.0;
        double meses      = Math.min(cd.getMesesComoSocio()    != null ? cd.getMesesComoSocio()    : 0, MAX_MESES)      / MAX_MESES;
        double tendenciaNorm = Math.max(0.0, Math.min(1.0, (-tendencia + 100.0) / 200.0));

        double z = BIAS
                + W_SEMANAS    * semanas
                + W_FRECUENCIA * (1.0 - frecuencia)
                + W_TENDENCIA  * tendenciaNorm
                + W_ANTIGUEDAD * meses;

        double prob = 1.0 / (1.0 + Math.exp(-z));

        return (Math.round(prob * 100.0) / 100.0);
    }

    @Override
    public NivelRiesgo calcularNivel(ClienteDatos clienteDatos)
    {
        double prob = calcularProbabilidad(clienteDatos);

        if (prob >= UMBRAL_ALTO)
            return NivelRiesgo.ALTO;
        else if (prob >= UMBRAL_MEDIO)
            return NivelRiesgo.MEDIO;
        else
            return NivelRiesgo.BAJO;
    }

    @Override
    public GrupoRiesgo asignarGrupo(ClienteDatos clienteDatos)
    {
        int    semanas    = clienteDatos.getSemanasInactivo()   != null ? clienteDatos.getSemanasInactivo()   : 0;
        int    meses      = clienteDatos.getMesesComoSocio()    != null ? clienteDatos.getMesesComoSocio()    : 0;
        double frecuencia = clienteDatos.getFrecuenciaSemanal() != null ? clienteDatos.getFrecuenciaSemanal() : 0.0;

        if (meses > MESES_CONSOLIDADO && semanas >= SEMANAS_AUSENCIA)
            // G1: socio consolidado que ha dejado de venir — máxima prioridad de retención
            return GrupoRiesgo.CONSOLIDADO_EN_RIESGO;

        if (meses <= MESES_NUEVO && frecuencia < FREC_MIN_ENGANCHE)
            // G2: socio nuevo sin enganche — foco en bienvenida y fidelización
            return GrupoRiesgo.NUEVO_SIN_ENGANCHE;

        if (frecuencia >= FREC_MIN_ESTABLE && semanas == 0 && meses >= MESES_MIN_PATRON)
            // G3: mínimo 4 meses para considerar el patrón de asistencia como consolidado
            return GrupoRiesgo.ACTIVO_ESTABLE;

        return GrupoRiesgo.IRREGULAR;
    }
}
