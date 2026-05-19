package com.juandelacierva.ChurnGym.service;

import com.juandelacierva.ChurnGym.domain.Usuario;
import com.juandelacierva.ChurnGym.dto.PerfilResponseDto;
import com.juandelacierva.ChurnGym.dto.PerfilUpdateDto;
import com.juandelacierva.ChurnGym.exception.ResourceNotFoundException;
import com.juandelacierva.ChurnGym.repository.UsuarioRepository;
import com.juandelacierva.ChurnGym.service.interfaces.PerfilService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PerfilServiceImpl implements PerfilService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public PerfilResponseDto getPerfil(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        return toDto(usuario);
    }

    @Override
    public PerfilResponseDto updatePerfil(String username, PerfilUpdateDto dto) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        usuario.setNombreGimnasio(dto.getNombreGimnasio());
        usuario.setDireccion1(dto.getDireccion1());
        usuario.setDireccion2(dto.getDireccion2());
        usuario.setTelefono(dto.getTelefono());

        return toDto(usuarioRepository.save(usuario));
    }

    private PerfilResponseDto toDto(Usuario u) {
        return PerfilResponseDto.builder()
                .username(u.getUsername())
                .email(u.getEmail())
                .nombreGimnasio(u.getNombreGimnasio())
                .direccion1(u.getDireccion1())
                .direccion2(u.getDireccion2())
                .telefono(u.getTelefono())
                .createdAt(u.getCreatedAt())
                .updatedAt(u.getUpdatedAt())
                .build();
    }
}
