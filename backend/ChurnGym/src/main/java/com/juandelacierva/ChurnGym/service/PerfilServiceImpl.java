package com.juandelacierva.ChurnGym.service;

import com.juandelacierva.ChurnGym.domain.Usuario;
import com.juandelacierva.ChurnGym.dto.PerfilResponseDto;
import com.juandelacierva.ChurnGym.dto.PerfilUpdateDto;
import com.juandelacierva.ChurnGym.exception.ResourceNotFoundException;
import com.juandelacierva.ChurnGym.mapper.PerfilMapper;
import com.juandelacierva.ChurnGym.repository.UsuarioRepository;
import com.juandelacierva.ChurnGym.service.interfaces.PerfilService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PerfilServiceImpl implements PerfilService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilMapper      perfilMapper;

    @Override
    public PerfilResponseDto getPerfil(String username) {
        return perfilMapper.toDto(getUsuario(username));
    }

    @Override
    public PerfilResponseDto updatePerfil(String username, PerfilUpdateDto dto) {
        Usuario usuario = getUsuario(username);
        perfilMapper.updateUsuario(dto, usuario);
        return perfilMapper.toDto(usuarioRepository.save(usuario));
    }

    private Usuario getUsuario(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }
}
