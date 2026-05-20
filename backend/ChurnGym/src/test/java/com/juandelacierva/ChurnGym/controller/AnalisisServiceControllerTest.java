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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Excluimos seguridad y filtros JWT para testear la capa HTTP de forma aislada
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

    @MockitoBean
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

    @Test
    @DisplayName("GET /api/analisis/vigente - Debe devolver 200")
    void shouldReturn200WhenGetAnalisisVigente() throws Exception
    {
        when(analisisService.getAnalisisVigente(any(), any(), any(), any(), any(), any())).thenReturn(resumenDto);

        mockMvc.perform(get("/api/analisis/vigente")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /api/analisis/lanzar - Debe devolver 200")
    void shouldReturn200WhenLanzarAnalisis() throws Exception
    {
        when(analisisService.lanzarAnalisis()).thenReturn(resumenDto);

        mockMvc.perform(post("/api/analisis/lanzar")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/analisis/cliente/{id} - Debe devolver 200")
    void shouldReturn200WhenGetDetalleCliente() throws Exception
    {
        when(analisisService.getDetalleCliente(1L))
            .thenReturn(new ClienteAnalisisResponseDto());

        mockMvc.perform(get("/api/analisis/cliente/{clienteId}", 1L)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/analisis/cliente/{id} - Debe devolver 404 cuando el cliente no existe")
    void shouldReturn404WhenClienteNotFound() throws Exception
    {
        when(analisisService.getDetalleCliente(99L))
            .thenThrow(new ResourceNotFoundException("No se encontró análisis para el cliente con id: 99"));

        mockMvc.perform(get("/api/analisis/cliente/{clienteId}", 99L)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("No se encontró análisis para el cliente con id: 99"));
    }
}
