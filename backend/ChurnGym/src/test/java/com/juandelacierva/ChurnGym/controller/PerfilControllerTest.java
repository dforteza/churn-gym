package com.juandelacierva.ChurnGym.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juandelacierva.ChurnGym.dto.PerfilResponseDto;
import com.juandelacierva.ChurnGym.dto.PerfilUpdateDto;
import com.juandelacierva.ChurnGym.security.jwt.JwtAuthenticationFilter;
import com.juandelacierva.ChurnGym.security.jwt.JwtGenerator;
import com.juandelacierva.ChurnGym.service.interfaces.PerfilService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
    controllers = PerfilController.class,
    excludeAutoConfiguration = UserDetailsServiceAutoConfiguration.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = {JwtAuthenticationFilter.class, JwtGenerator.class}
    )
)
class PerfilControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PerfilService perfilService;

    // ==========================================================================
    // GET /api/v1/perfil
    // ==========================================================================

    @Test
    @WithMockUser(username = "diego")
    @DisplayName("GET /api/v1/perfil - Debe devolver 200 con los datos del perfil")
    void shouldReturn200WithPerfilData() throws Exception
    {
        PerfilResponseDto perfil = PerfilResponseDto.builder()
                .username("diego")
                .email("diego@churngym.dev")
                .nombreGimnasio("Churn Gym Madrid Centro")
                .build();

        when(perfilService.getPerfil("diego")).thenReturn(perfil);

        mockMvc.perform(get("/api/v1/perfil"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("diego"))
            .andExpect(jsonPath("$.nombreGimnasio").value("Churn Gym Madrid Centro"));
    }

    // ==========================================================================
    // PUT /api/v1/perfil
    // ==========================================================================

    @Test
    @WithMockUser(username = "diego")
    @DisplayName("PUT /api/v1/perfil - Debe devolver 200 con el perfil actualizado")
    void shouldReturn200WhenValidUpdate() throws Exception
    {
        PerfilUpdateDto updateDto = new PerfilUpdateDto(
                "Nuevo Nombre", "Nueva Dir", "Madrid, 28001", "+34910000001");

        PerfilResponseDto updated = PerfilResponseDto.builder()
                .username("diego")
                .nombreGimnasio("Nuevo Nombre")
                .build();

        when(perfilService.updatePerfil(eq("diego"), any())).thenReturn(updated);

        mockMvc.perform(put("/api/v1/perfil")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombreGimnasio").value("Nuevo Nombre"));
    }

    @Test
    @WithMockUser(username = "diego")
    @DisplayName("PUT /api/v1/perfil - Debe devolver 400 si el teléfono tiene formato incorrecto")
    void shouldReturn400WhenInvalidPhone() throws Exception
    {
        PerfilUpdateDto invalidDto = new PerfilUpdateDto(
                "Nombre", "Dir", "Ciudad", "+34 910 123 456");

        mockMvc.perform(put("/api/v1/perfil")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDto)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value(400));

        verifyNoInteractions(perfilService);
    }
}
