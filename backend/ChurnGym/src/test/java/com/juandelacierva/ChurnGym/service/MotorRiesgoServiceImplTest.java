package com.juandelacierva.ChurnGym.service;

import com.juandelacierva.ChurnGym.domain.ClienteDatos;
import com.juandelacierva.ChurnGym.domain.enums.GrupoRiesgo;
import com.juandelacierva.ChurnGym.domain.enums.NivelRiesgo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MotorRiesgoServiceImplTest 
{
    private MotorRiesgoServiceImpl motor;

    @BeforeEach
    void setUp() 
    {
        motor = new MotorRiesgoServiceImpl();
    }

    // ==========================================================================
    // calcularProbabilidad
    // ==========================================================================

    @Test
    @DisplayName("Debe devolver probabilidad alta para el socio fantasma (máximo riesgo)")
    void shouldReturnHighProbabilidadWhenSocioIsInactivo() 
    {
        // Arrange - socio fantasma
        ClienteDatos cd = ClienteDatos.builder()
                .semanasInactivo(12)
                .frecuenciaSemanal(0.0)
                .tendenciaMensual(-100.0)
                .mesesComoSocio(1)
                .build();

        // Act
        Double prob = motor.calcularProbabilidad(cd);

        // Assert
        assertNotNull(prob);
        assertTrue(prob >= 0.65, "Se esperaba probabilidad ALTA (>= 0.65), resultado: " + prob);
    }

    @Test
    @DisplayName("Debe devolver probabilidad baja para el socio ideal (mínimo riesgo)")
    void shouldReturnLowProbabilidadWhenSocioIsActivo() 
    {
        // Arrange - socio ideal
        ClienteDatos cd = ClienteDatos.builder()
                .semanasInactivo(0)
                .frecuenciaSemanal(7.0)
                .tendenciaMensual(50.0)
                .mesesComoSocio(24)
                .build();

        // Act
        Double prob = motor.calcularProbabilidad(cd);

        // Assert
        assertNotNull(prob);
        assertTrue(prob < 0.30, "Se esperaba probabilidad BAJA (< 0.30), resultado: " + prob);
    }

    @Test
    @DisplayName("Debe manejar todos los campos nulos sin lanzar excepción")
    void shouldHandleAllNullFieldsWithoutThrowing() 
    {
        // Arrange - socio con campos nulos
        ClienteDatos cd = ClienteDatos.builder().build();

        // Act & Assert
        assertDoesNotThrow(() -> {
            Double prob = motor.calcularProbabilidad(cd);
            assertNotNull(prob);
            assertTrue(prob >= 0.0 && prob <= 1.0,
                    "La probabilidad debe estar en [0.0, 1.0], resultado: " + prob);
        });
    }

    // ==========================================================================
    // calcularNivel
    // ==========================================================================

    @Test
    @DisplayName("Debe devolver ALTO cuando la probabilidad supera 0.65")
    void shouldReturnAltoWhenProbabilidadIsAboveUmbralAlto() 
    {
        // Arrange - socio fantasma
        ClienteDatos cd = ClienteDatos.builder()
                .semanasInactivo(12)
                .frecuenciaSemanal(0.0)
                .tendenciaMensual(-100.0)
                .mesesComoSocio(1)
                .build();

        // Act
        NivelRiesgo nivel = motor.calcularNivel(cd);

        // Assert
        assertEquals(NivelRiesgo.ALTO, nivel);
    }

    @Test
    @DisplayName("Debe devolver BAJO cuando la probabilidad está por debajo de 0.30")
    void shouldReturnBajoWhenProbabilidadIsBelowUmbralMedio() 
    {
        // Arrange - socio ideal
        ClienteDatos cd = ClienteDatos.builder()
                .semanasInactivo(0)
                .frecuenciaSemanal(7.0)
                .tendenciaMensual(50.0)
                .mesesComoSocio(24)
                .build();

        // Act
        NivelRiesgo nivel = motor.calcularNivel(cd);

        // Assert
        assertEquals(NivelRiesgo.BAJO, nivel);
    }

    @Test
    @DisplayName("Debe devolver MEDIO cuando la probabilidad está entre 0.30 y 0.65")
    void shouldReturnMedioWhenProbabilidadIsBetweenUmbrales() 
    {
        // Arrange - socio con riesgo moderado: 3 semanas inactivo, poca frecuencia, tendencia leve
        // Cálculo esperado: z ≈ 0.14 -> prob ≈ 0.53 -> MEDIO
        ClienteDatos cd = ClienteDatos.builder()
                .semanasInactivo(3)
                .frecuenciaSemanal(2.0)
                .tendenciaMensual(-10.0)
                .mesesComoSocio(8)
                .build();

        // Act
        NivelRiesgo nivel = motor.calcularNivel(cd);

        // Assert
        assertEquals(NivelRiesgo.MEDIO, nivel);
    }

    // ==========================================================================
    // asignarGrupo
    // ==========================================================================

    @Test
    @DisplayName("Debe asignar CONSOLIDADO_EN_RIESGO a socio veterano con 3+ semanas de ausencia")
    void shouldReturnConsolidadoEnRiesgoWhenLongTermMemberIsAbsent() 
    {
        // Arrange - más de 12 meses y 4 semanas sin venir
        ClienteDatos cd = ClienteDatos.builder()
                .mesesComoSocio(24)
                .semanasInactivo(4)
                .frecuenciaSemanal(3.0)
                .build();

        // Act
        GrupoRiesgo grupo = motor.asignarGrupo(cd);

        // Assert
        assertEquals(GrupoRiesgo.CONSOLIDADO_EN_RIESGO, grupo);
    }

    @Test
    @DisplayName("Debe asignar NUEVO_SIN_ENGANCHE a socio nuevo que no ha cogido el hábito")
    void shouldReturnNuevoSinEngancheWhenNewMemberHasLowFrequency() 
    {
        // Arrange - 2 meses de socio y menos de 1 visita semanal
        ClienteDatos cd = ClienteDatos.builder()
                .mesesComoSocio(2)
                .semanasInactivo(1)
                .frecuenciaSemanal(0.5)
                .build();

        // Act
        GrupoRiesgo grupo = motor.asignarGrupo(cd);

        // Assert
        assertEquals(GrupoRiesgo.NUEVO_SIN_ENGANCHE, grupo);
    }

    @Test
    @DisplayName("Debe asignar ACTIVO_ESTABLE a socio con patrón de asistencia consolidado")
    void shouldReturnActivoEstableWhenMemberIsRegularAndEstablished() 
    {
        // Arrange - 6 meses, sin ausencias esta semana y 3 visitas/semana
        ClienteDatos cd = ClienteDatos.builder()
                .mesesComoSocio(6)
                .semanasInactivo(0)
                .frecuenciaSemanal(3.0)
                .build();

        // Act
        GrupoRiesgo grupo = motor.asignarGrupo(cd);

        // Assert
        assertEquals(GrupoRiesgo.ACTIVO_ESTABLE, grupo);
    }

    @Test
    @DisplayName("Debe asignar IRREGULAR cuando el socio no encaja en ningún grupo")
    void shouldReturnIrregularWhenNoGroupRuleMatches() 
    {
        // Arrange - 6 meses (no es nuevo ni veterano), 1 semana ausente, frecuencia intermedia
        ClienteDatos cd = ClienteDatos.builder()
                .mesesComoSocio(6)
                .semanasInactivo(1)
                .frecuenciaSemanal(1.5)
                .build();

        // Act
        GrupoRiesgo grupo = motor.asignarGrupo(cd);

        // Assert
        assertEquals(GrupoRiesgo.IRREGULAR, grupo);
    }

    @Test
    @DisplayName("Debe priorizar CONSOLIDADO_EN_RIESGO aunque la frecuencia histórica sea alta")
    void shouldPrioritizeConsolidadoEnRiesgoOverActivoEstable()
    {
        // Arrange - 18 meses y 3 semanas sin venir: aunque venía 4 veces/semana,
        // G1 se evalúa primero y gana porque la ausencia supera el umbral
        ClienteDatos cd = ClienteDatos.builder()
                .mesesComoSocio(18)
                .semanasInactivo(3)
                .frecuenciaSemanal(4.0)
                .build();

        assertEquals(GrupoRiesgo.CONSOLIDADO_EN_RIESGO, motor.asignarGrupo(cd));
    }

    @Test
    @DisplayName("Debe asignar VETERANO_EN_PAUSA a veterano con exactamente 2 semanas de ausencia")
    void shouldReturnVeteranoEnPausaWhenTwoWeeksAbsent()
    {
        // Arrange - 18 meses, exactamente 2 semanas sin venir — ventana de intervención temprana
        ClienteDatos cd = ClienteDatos.builder()
                .mesesComoSocio(18)
                .semanasInactivo(2)
                .frecuenciaSemanal(3.0)
                .build();

        assertEquals(GrupoRiesgo.VETERANO_EN_PAUSA, motor.asignarGrupo(cd));
    }

    @Test
    @DisplayName("Debe asignar VETERANO_ESPORADICO a veterano activo con frecuencia baja")
    void shouldReturnVeteranoEsporadicoWhenLowFrequency()
    {
        // Arrange - 24 meses, viene esta semana pero solo 1 vez cada dos semanas
        ClienteDatos cd = ClienteDatos.builder()
                .mesesComoSocio(24)
                .semanasInactivo(0)
                .frecuenciaSemanal(0.8)
                .build();

        assertEquals(GrupoRiesgo.VETERANO_ESPORADICO, motor.asignarGrupo(cd));
    }

    @Test
    @DisplayName("Debe asignar NUEVO_ENGANCHADO a socio nuevo que viene regularmente")
    void shouldReturnNuevoEnganchadoWhenNewMemberIsActive()
    {
        // Arrange - 2 meses, viene esta semana, frecuencia de 2 veces/semana
        ClienteDatos cd = ClienteDatos.builder()
                .mesesComoSocio(2)
                .semanasInactivo(0)
                .frecuenciaSemanal(2.0)
                .build();

        assertEquals(GrupoRiesgo.NUEVO_ENGANCHADO, motor.asignarGrupo(cd));
    }

    @Test
    @DisplayName("Debe asignar IRREGULAR a veterano con solo 1 semana de ausencia (señal insuficiente)")
    void shouldReturnIrregularWhenVeteranoHasOneWeekAbsence()
    {
        // Arrange - 18 meses, 1 semana sin venir — demasiado poco para ser señal real
        ClienteDatos cd = ClienteDatos.builder()
                .mesesComoSocio(18)
                .semanasInactivo(1)
                .frecuenciaSemanal(3.0)
                .build();

        assertEquals(GrupoRiesgo.IRREGULAR, motor.asignarGrupo(cd));
    }
}
