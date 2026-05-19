package com.juandelacierva.ChurnGym.service;

import com.juandelacierva.ChurnGym.domain.Usuario;
import com.juandelacierva.ChurnGym.domain.enums.Rol;
import com.juandelacierva.ChurnGym.dto.PerfilResponseDto;
import com.juandelacierva.ChurnGym.dto.PerfilUpdateDto;
import com.juandelacierva.ChurnGym.exception.ResourceNotFoundException;
import com.juandelacierva.ChurnGym.mapper.PerfilMapper;
import com.juandelacierva.ChurnGym.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PerfilServiceImplTest
{
    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PerfilMapper perfilMapper;

    @InjectMocks
    private PerfilServiceImpl perfilService;

    private Usuario usuario;
    private PerfilResponseDto perfilResponseDto;

    @BeforeEach
    void setUp()
    {
        usuario = new Usuario();
        usuario.setUsername("diego");
        usuario.setEmail("diego@churngym.dev");
        usuario.setPassword("hashed");
        usuario.setRol(Rol.ADMIN);
        usuario.setNombreGimnasio("Churn Gym Madrid Centro");
        usuario.setDireccion1("Calle Gran Vía, 45");
        usuario.setDireccion2("Madrid, 28013");
        usuario.setTelefono("+34910123456");

        perfilResponseDto = PerfilResponseDto.builder()
                .username("diego")
                .email("diego@churngym.dev")
                .nombreGimnasio("Churn Gym Madrid Centro")
                .build();
    }

    // ==========================================================================
    // getPerfil
    // ==========================================================================

    @Test
    @DisplayName("Debe devolver el DTO cuando el usuario existe")
    void shouldReturnDtoWhenUsuarioExists()
    {
        when(usuarioRepository.findByUsername("diego")).thenReturn(Optional.of(usuario));
        when(perfilMapper.toDto(usuario)).thenReturn(perfilResponseDto);

        PerfilResponseDto result = perfilService.getPerfil("diego");

        assertNotNull(result);
        assertEquals("diego", result.getUsername());
        verify(usuarioRepository, times(1)).findByUsername("diego");
        verify(perfilMapper, times(1)).toDto(usuario);
    }

    @Test
    @DisplayName("Debe lanzar ResourceNotFoundException cuando el usuario no existe")
    void shouldThrowWhenUsuarioNotFound()
    {
        when(usuarioRepository.findByUsername("noexiste")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> perfilService.getPerfil("noexiste"));

        verify(usuarioRepository, times(1)).findByUsername("noexiste");
        verifyNoInteractions(perfilMapper);
    }

    // ==========================================================================
    // updatePerfil
    // ==========================================================================

    @Test
    @DisplayName("Debe aplicar el mapper, guardar y devolver el DTO actualizado")
    void shouldUpdateAndReturnDto()
    {
        PerfilUpdateDto updateDto = new PerfilUpdateDto(
                "Nuevo Nombre", "Nueva Dir 1", "Nueva Dir 2", "+34600123456");

        when(usuarioRepository.findByUsername("diego")).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        when(perfilMapper.toDto(usuario)).thenReturn(perfilResponseDto);

        PerfilResponseDto result = perfilService.updatePerfil("diego", updateDto);

        assertNotNull(result);
        verify(perfilMapper, times(1)).updateUsuario(updateDto, usuario);
        verify(usuarioRepository, times(1)).save(usuario);
        verify(perfilMapper, times(1)).toDto(usuario);
    }

    @Test
    @DisplayName("Debe lanzar ResourceNotFoundException en update si el usuario no existe")
    void shouldThrowInUpdateWhenUsuarioNotFound()
    {
        when(usuarioRepository.findByUsername("noexiste")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> perfilService.updatePerfil("noexiste", new PerfilUpdateDto()));

        verify(usuarioRepository, times(1)).findByUsername("noexiste");
        verifyNoInteractions(perfilMapper);
    }
}
