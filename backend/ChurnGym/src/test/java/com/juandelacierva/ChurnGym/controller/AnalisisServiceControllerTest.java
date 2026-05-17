package com.juandelacierva.ChurnGym.controller;

import com.juandelacierva.ChurnGym.dto.AnalisisResumenResponseDto;
import com.juandelacierva.ChurnGym.dto.ClienteAnalisisResponseDto;
import com.juandelacierva.ChurnGym.exception.ResourceNotFoundException;
import com.juandelacierva.ChurnGym.security.jwt.JwtAuthenticationFilter;
import com.juandelacierva.ChurnGym.security.jwt.JwtGenerator;
import com.juandelacierva.ChurnGym.service.interfaces.AnalisisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
    controllers = AnalisisServiceController.class,
    excludeAutoConfiguration = {
        SecurityAutoConfiguration.class,
        UserDetailsServiceAutoConfiguration.class
    },
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = {JwtAuthenticationFilter.class, JwtGenerator.class}
    )
)
class AnalisisServiceControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnalisisService analisisService;

    private AnalisisResumenResponseDto resumenDto;

    @BeforeEach
    void setUp()
    {
        resumenDto = new AnalisisResumenResponseDto(
            LocalDateTime.now(), 2, 1, 1, 0,
            new PageImpl<>(List.of())
        );
    }

    // ==========================================================================
    // GET /api/analisis/vigente
    // ==========================================================================

    @Test
    @DisplayName("GET /api/analisis/vigente - Debe devolver 200 con el resumen")
    void shouldReturn200WhenGetAnalisisVigente() throws Exception
    {
        // Arrange
        when(analisisService.getAnalisisVigente(any(), any(), any(), any(), any())).thenReturn(resumenDto);

        // Act & Assert
        mockMvc.perform(get("/api/analisis/vigente")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.totalClientes").value(2))
            .andExpect(jsonPath("$.alto").value(1));

        verify(analisisService, times(1)).getAnalisisVigente(any(), any(), any(), any(), any());
    }

    // ==========================================================================
    // POST /api/analisis/lanzar
    // ==========================================================================

    @Test
    @DisplayName("POST /api/analisis/lanzar - Debe devolver 200 al lanzar el análisis")
    void shouldReturn200WhenLanzarAnalisis() throws Exception
    {
        // Arrange
        when(analisisService.lanzarAnalisis()).thenReturn(resumenDto);

        // Act & Assert
        mockMvc.perform(post("/api/analisis/lanzar")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(analisisService, times(1)).lanzarAnalisis();
    }

    // ==========================================================================
    // GET /api/analisis/cliente/{clienteId}
    // ==========================================================================

    @Test
    @DisplayName("GET /api/analisis/cliente/{id} - Debe devolver 200 con el detalle del cliente")
    void shouldReturn200WhenGetDetalleCliente() throws Exception
    {
        // Arrange
        when(analisisService.getDetalleCliente(1L)).thenReturn(new ClienteAnalisisResponseDto());

        // Act & Assert
        mockMvc.perform(get("/api/analisis/cliente/{clienteId}", 1L)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(analisisService, times(1)).getDetalleCliente(1L);
    }

    @Test
    @DisplayName("GET /api/analisis/cliente/{id} - Debe devolver 404 cuando el cliente no existe")
    void shouldReturn404WhenClienteNotFound() throws Exception
    {
        // Arrange — el service lanza la excepción, el GlobalExceptionHandler la convierte en 404
        when(analisisService.getDetalleCliente(99L))
            .thenThrow(new ResourceNotFoundException("No se encontró análisis para el cliente con id: 99"));

        // Act & Assert
        mockMvc.perform(get("/api/analisis/cliente/{clienteId}", 99L)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("No se encontró análisis para el cliente con id: 99"));

        verify(analisisService, times(1)).getDetalleCliente(99L);
    }
}
