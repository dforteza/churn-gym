package com.juandelacierva.ChurnGym.service;

import com.juandelacierva.ChurnGym.domain.ClienteDatos;
import com.juandelacierva.ChurnGym.domain.ClientePrivado;
import com.juandelacierva.ChurnGym.domain.ResultadoAnalisis;
import com.juandelacierva.ChurnGym.domain.enums.GrupoRiesgo;
import com.juandelacierva.ChurnGym.domain.enums.NivelRiesgo;
import com.juandelacierva.ChurnGym.dto.AnalisisResumenResponseDto;
import com.juandelacierva.ChurnGym.dto.ClienteAnalisisResponseDto;
import com.juandelacierva.ChurnGym.exception.ResourceNotFoundException;
import com.juandelacierva.ChurnGym.mapper.AnalisisMapper;
import com.juandelacierva.ChurnGym.repository.ClienteDatosRepository;
import com.juandelacierva.ChurnGym.repository.ResultadoAnalisisRepository;
import com.juandelacierva.ChurnGym.service.interfaces.MotorRiesgoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnalisisServiceImplTest
{
    @Mock
    private ClienteDatosRepository      clienteDatosRepository;
    @Mock
    private ResultadoAnalisisRepository resultadoAnalisisRepository;
    @Mock
    private MotorRiesgoService          motorRiesgoService;
    @Mock
    private ClusteringClient            clusteringClient;
    @Mock
    private AnalisisMapper              analisisMapper;

    @InjectMocks
    private AnalisisServiceImpl analisisService;

    private ClienteDatos                clienteDatos;
    private ResultadoAnalisis           resultado;
    private AnalisisResumenResponseDto  resumenDto;

    @BeforeEach
    void setUp()
    {
        ClientePrivado privado = ClientePrivado.builder()
                .id(1L)
                .nombre("Juan")
                .apellidos("García")
                .email("juan@test.com")
                .dni("12345678A")
                .build();

        clienteDatos = ClienteDatos.builder()
                .id(1L)
                .mesesComoSocio(6)
                .semanasInactivo(0)
                .frecuenciaSemanal(3.0)
                .clientePrivado(privado)
                .build();

        resultado = ResultadoAnalisis.builder()
                .id(1L)
                .clienteDatos(clienteDatos)
                .nivelRiesgo(NivelRiesgo.MEDIO)
                .probabilidadAbandono(0.45)
                .grupo(GrupoRiesgo.ACTIVO_ESTABLE)
                .calculadoEn(LocalDateTime.now())
                .build();

        resumenDto = new AnalisisResumenResponseDto();
    }

    // ==========================================================================
    // getAnalisisVigente
    // ==========================================================================

    @Test
    @DisplayName("Debe devolver resumen cuando existen resultados en BD")
    void shouldReturnResumenWhenResultsExist()
    {
        // Arrange
        Page<ResultadoAnalisis> pagina = new PageImpl<>(List.of(resultado));

        when(resultadoAnalisisRepository.findAll()).thenReturn(List.of(resultado));
        when(resultadoAnalisisRepository.findWithFilters(any(), any(), any(), any(), any())).thenReturn(pagina);
        when(analisisMapper.toAnalisisResumenResponse(any(), any(), any())).thenReturn(resumenDto);

        // Act
        AnalisisResumenResponseDto response = analisisService.getAnalisisVigente(
                null, null, null, null, PageRequest.of(0, 10));

        // Assert
        assertNotNull(response);
        verify(resultadoAnalisisRepository, times(1)).findAll();
        verify(analisisMapper, times(1)).toAnalisisResumenResponse(any(), any(), any());
    }

    @Test
    @DisplayName("Debe devolver resumen vacío sin lanzar excepción cuando no hay resultados")
    void shouldReturnEmptyResumenWhenNoResults()
    {
        // Arrange
        when(resultadoAnalisisRepository.findAll()).thenReturn(List.of());
        when(resultadoAnalisisRepository.findWithFilters(any(), any(), any(), any(), any())).thenReturn(Page.empty());
        when(analisisMapper.toAnalisisResumenResponse(any(), any(), any())).thenReturn(resumenDto);

        // Act & Assert
        assertDoesNotThrow(() -> analisisService.getAnalisisVigente(
                null, null, null, null, PageRequest.of(0, 10)));
    }

    // ==========================================================================
    // getDetalleCliente
    // ==========================================================================

    @Test
    @DisplayName("Debe devolver el detalle del cliente cuando existe su análisis")
    void shouldReturnDetalleWhenClienteExists()
    {
        // Arrange
        ClienteAnalisisResponseDto detalleDto = new ClienteAnalisisResponseDto();
        when(resultadoAnalisisRepository.findByClienteDatosId(1L)).thenReturn(Optional.of(resultado));
        when(analisisMapper.toClienteAnalisisResponse(any(), any())).thenReturn(detalleDto);

        // Act
        ClienteAnalisisResponseDto response = analisisService.getDetalleCliente(1L);

        // Assert
        assertNotNull(response);
        verify(resultadoAnalisisRepository, times(1)).findByClienteDatosId(1L);
        verify(analisisMapper, times(1)).toClienteAnalisisResponse(any(), any());
    }

    @Test
    @DisplayName("Debe lanzar ResourceNotFoundException cuando el cliente no tiene análisis")
    void shouldThrowExceptionWhenClienteNotFound()
    {
        // Arrange
        when(resultadoAnalisisRepository.findByClienteDatosId(99L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> analisisService.getDetalleCliente(99L)
        );

        assertTrue(thrown.getMessage().contains("99"));
        verify(resultadoAnalisisRepository, times(1)).findByClienteDatosId(99L);
    }

    // ==========================================================================
    // lanzarAnalisis
    // ==========================================================================

    @Test
    @DisplayName("Debe borrar los datos previos y persistir un resultado por cada cliente")
    void shouldDeleteOldDataAndPersistOneResultPerCliente()
    {
        // Arrange - 2 clientes en BD
        ClienteDatos cliente2 = ClienteDatos.builder()
                                        .id(2L)
                                        .build();
        List<ClienteDatos> clientes = List.of(clienteDatos, cliente2);

        when(clienteDatosRepository.findAll()).thenReturn(clientes);
        when(clusteringClient.obtenerGrupos(any())).thenReturn(Map.of());
        when(motorRiesgoService.calcularNivel(any())).thenReturn(NivelRiesgo.MEDIO);
        when(motorRiesgoService.calcularProbabilidad(any())).thenReturn(0.45);
        when(motorRiesgoService.asignarGrupo(any())).thenReturn(GrupoRiesgo.IRREGULAR);
        when(resultadoAnalisisRepository.saveAll(any())).thenReturn(List.of());
        when(resultadoAnalisisRepository.findWithFilters(any(), any(), any(), any(), any())).thenReturn(Page.empty());
        when(analisisMapper.toAnalisisResumenResponse(any(), any(), any())).thenReturn(resumenDto);

        // Act
        analisisService.lanzarAnalisis();

        // Assert
        verify(resultadoAnalisisRepository, times(1)).deleteAllInBatch();
        verify(resultadoAnalisisRepository, times(1)).saveAll(any());
        verify(motorRiesgoService, times(2)).calcularNivel(any());
        verify(motorRiesgoService, times(2)).calcularProbabilidad(any());
    }

    @Test
    @DisplayName("Debe usar el motor de riesgo como fallback cuando clustering no cubre al cliente")
    void shouldUseFallbackGrupoWhenClienteNotInClustering()
    {
        // Arrange - clustering devuelve mapa vacío: todos los clientes van al fallback
        when(clienteDatosRepository.findAll()).thenReturn(List.of(clienteDatos));
        when(clusteringClient.obtenerGrupos(any())).thenReturn(Map.of());
        when(motorRiesgoService.calcularNivel(any())).thenReturn(NivelRiesgo.BAJO);
        when(motorRiesgoService.calcularProbabilidad(any())).thenReturn(0.2);
        when(motorRiesgoService.asignarGrupo(any())).thenReturn(GrupoRiesgo.ACTIVO_ESTABLE);
        when(resultadoAnalisisRepository.saveAll(any())).thenReturn(List.of());
        when(resultadoAnalisisRepository.findWithFilters(any(), any(), any(), any(), any())).thenReturn(Page.empty());
        when(analisisMapper.toAnalisisResumenResponse(any(), any(), any())).thenReturn(resumenDto);

        // Act
        analisisService.lanzarAnalisis();

        // Assert
        verify(motorRiesgoService, times(1)).asignarGrupo(any());
    }
}
