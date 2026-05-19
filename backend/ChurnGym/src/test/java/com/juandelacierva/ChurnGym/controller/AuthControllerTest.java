package com.juandelacierva.ChurnGym.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juandelacierva.ChurnGym.security.dto.LoginRequestDto;
import com.juandelacierva.ChurnGym.security.dto.LoginResponseDto;
import com.juandelacierva.ChurnGym.security.jwt.JwtAuthenticationFilter;
import com.juandelacierva.ChurnGym.security.jwt.JwtGenerator;
import com.juandelacierva.ChurnGym.service.interfaces.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Excluimos seguridad y filtros JWT para testear la capa HTTP de forma aislada
@WebMvcTest(
    controllers = AuthController.class,
    excludeAutoConfiguration = {
        SecurityAutoConfiguration.class,            // seguridad de Spring Boot
        UserDetailsServiceAutoConfiguration.class   // servicio de usuarios de Spring Security
    },
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = {JwtAuthenticationFilter.class, JwtGenerator.class}   // filtros de autenticación y generación de JWT
    )
)
class AuthControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthService authService;

    @Test
    @DisplayName("POST /api/v1/auth/login - Debe devolver 200 y token con credenciales válidas")
    void shouldReturn200WithTokenWhenValidCredentials() throws Exception
    {
        LoginRequestDto request  = new LoginRequestDto("admin", "password123");
        LoginResponseDto response = new LoginResponseDto("jwt-token", "admin", "ADMIN");

        when(authService.login(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value("jwt-token"));
    }

    @Test
    @DisplayName("POST /api/v1/auth/login - Debe devolver 401 con credenciales incorrectas")
    void shouldReturn401WhenInvalidCredentials() throws Exception
    {
        when(authService.login(any())).thenThrow(new BadCredentialsException("Credenciales incorrectas"));

        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new LoginRequestDto("admin", "mal"))))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.status").value(401));
    }

    @Test
    @DisplayName("POST /api/v1/auth/login - Debe devolver 400 si username o password están vacíos")
    void shouldReturn400WhenBodyFailsValidation() throws Exception
    {
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value(400));

        verifyNoInteractions(authService);
    }
}
