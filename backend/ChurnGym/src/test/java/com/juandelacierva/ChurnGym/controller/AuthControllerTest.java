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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
    controllers = AuthController.class,
    excludeAutoConfiguration = {
        SecurityAutoConfiguration.class,
        UserDetailsServiceAutoConfiguration.class
    },
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = {JwtAuthenticationFilter.class, JwtGenerator.class}
    )
)
class AuthControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @Test
    @DisplayName("POST /api/v1/auth/login - Debe devolver 200 y token con credenciales válidas")
    void shouldReturn200WithTokenWhenValidCredentials() throws Exception
    {
        // Arrange
        LoginRequestDto request = new LoginRequestDto("admin", "password123");
        LoginResponseDto response = new LoginResponseDto("jwt-token-generado", "admin", "ADMIN");

        when(authService.login(any())).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value("jwt-token-generado"))
            .andExpect(jsonPath("$.username").value("admin"))
            .andExpect(jsonPath("$.rol").value("ADMIN"));

        verify(authService, times(1)).login(any());
    }

    @Test
    @DisplayName("POST /api/v1/auth/login - Debe devolver 401 con credenciales incorrectas")
    void shouldReturn401WhenInvalidCredentials() throws Exception
    {
        // Arrange — el service lanza BadCredentialsException, el GlobalExceptionHandler devuelve 401
        LoginRequestDto request = new LoginRequestDto("admin", "contraseñaMal");

        when(authService.login(any())).thenThrow(new BadCredentialsException("Credenciales incorrectas"));

        // Act & Assert
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.status").value(401))
            .andExpect(jsonPath("$.message").value("Credenciales incorrectas"));
    }

    @Test
    @DisplayName("POST /api/v1/auth/login - Debe devolver 400 si username o password están vacíos")
    void shouldReturn400WhenBodyFailsValidation() throws Exception
    {
        // Arrange — body sin username ni password dispara @NotBlank → GlobalExceptionHandler → 400
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.errors.username").exists())
            .andExpect(jsonPath("$.errors.password").exists());

        verifyNoInteractions(authService);
    }
}
