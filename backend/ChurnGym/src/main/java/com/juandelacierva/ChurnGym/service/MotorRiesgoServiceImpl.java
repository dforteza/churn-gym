package com.juandelacierva.ChurnGym.service;

import com.juandelacierva.ChurnGym.domain.ClienteDatos;
import com.juandelacierva.ChurnGym.domain.NivelRiesgo;
import com.juandelacierva.ChurnGym.service.interfaces.MotorRiesgoService;

import org.springframework.stereotype.Service;

/**
 * BLOQUE 1 — Motor de Reglas de Riesgo (Java)
 *
 * Responsabilidad: calcular la probabilidad de abandono y el nivel de riesgo
 * de cada cliente mediante un sistema de reglas explícitas y ponderadas.
 *
 * Este componente es independiente del microservicio de clustering Python.
 * Ambos coexisten: las reglas determinan el RIESGO, el clustering determina el GRUPO.
 */
@Service
public class MotorRiesgoServiceImpl implements MotorRiesgoService {

    // Umbrales de clasificación sobre la probabilidad calculada (0.0 – 1.0)
    private static final double UMBRAL_ALTO  = 0.65;
    private static final double UMBRAL_MEDIO = 0.30;

    /**
     * Calcula la probabilidad de abandono del cliente (0.0 = sin riesgo, 1.0 = abandono seguro).
     *
     * Fórmula ponderada sobre tres factores normalizados:
     *   - Inactividad  (40%): semanas sin asistir         → 0 sem = 0.0 | 8+ sem = 1.0
     *   - Tendencia    (35%): evolución mensual asistencia → +20% = 0.0  | -70%  = 1.0
     *   - Frecuencia   (25%): visitas semanales           → ≥4/sem = 0.0 | 0/sem = 1.0
     *
     * Corrección adicional: socios con menos de 3 meses suman +0.05
     * por mayor incertidumbre de permanencia en la fase de captación.
     */
    @Override
    public Double calcularProbabilidad(ClienteDatos datos) {
        // Factor 1: semanas de inactividad  — 0 sem → 0.0, 8+ sem → 1.0
        double riesgoInactividad = Math.min(datos.getSemanasInactivo() / 8.0, 1.0);

        // Factor 2: tendencia mensual de asistencia — +20% → 0.0, -70% → 1.0
        double riesgoTendencia = Math.min(Math.max((20.0 - datos.getTendenciaMensual()) / 90.0, 0.0), 1.0);

        // Factor 3: frecuencia semanal — ≥4 días → 0.0, 0 días → 1.0
        double riesgoFrecuencia = Math.min(Math.max((4.0 - datos.getFrecuenciaSemanal()) / 4.0, 0.0), 1.0);

        double probabilidad = 0.40 * riesgoInactividad
                            + 0.35 * riesgoTendencia
                            + 0.25 * riesgoFrecuencia;

        // Corrección: socios nuevos (< 3 meses) tienen mayor incertidumbre de abandono
        if (datos.getMesesComoSocio() != null && datos.getMesesComoSocio() < 3) {
            probabilidad = Math.min(probabilidad + 0.05, 1.0);
        }

        return Math.round(probabilidad * 100.0) / 100.0;
    }

    /**
     * Clasifica el nivel de riesgo derivándolo de la probabilidad calculada.
     *   - ALTO  : prob ≥ 0.65
     *   - MEDIO : prob ≥ 0.30
     *   - BAJO  : prob < 0.30
     */
    @Override
    public NivelRiesgo calcularNivel(ClienteDatos datos) {
        double prob = calcularProbabilidad(datos);
        if (prob >= UMBRAL_ALTO)  return NivelRiesgo.ALTO;
        if (prob >= UMBRAL_MEDIO) return NivelRiesgo.MEDIO;
        return NivelRiesgo.BAJO;
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
    public String asignarGrupo(ClienteDatos datos) {
        int    semanas    = datos.getSemanasInactivo() != null ? datos.getSemanasInactivo() : 0;
        int    meses      = datos.getMesesComoSocio()  != null ? datos.getMesesComoSocio()  : 0;
        double frecuencia = datos.getFrecuenciaSemanal() != null ? datos.getFrecuenciaSemanal() : 0.0;

        // G1: socio consolidado que ha dejado de venir — máxima prioridad de retención
        if (meses > 12 && semanas >= 3)           return "CONSOLIDADO_EN_RIESGO";

        // G2: socio nuevo que no está enganchando
        if (meses <= 3 && frecuencia < 1.0)       return "NUEVO_SIN_ENGANCHE";

        // G3: socio activo y sin ausencias recientes
        if (frecuencia >= 2.0 && semanas == 0)    return "ACTIVO_ESTABLE";

        // G4: red de seguridad — patrón irregular no clasificable en los anteriores
        return "IRREGULAR";
    }
}
