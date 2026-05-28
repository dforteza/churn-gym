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
public class PerfilServiceImpl implements PerfilService 
{
    private final UsuarioRepository usuarioRepository;
    private final PerfilMapper      perfilMapper;

    @Override
    public PerfilResponseDto getPerfil(String username) 
    {
        Usuario usuario = usuarioRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        
        return (perfilMapper.toDto(usuario));
    }

    @Override
    public PerfilResponseDto updatePerfil(String username, PerfilUpdateDto dto) 
    {
        Usuario usuario = usuarioRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        
        perfilMapper.updateUsuario(dto, usuario);

        Usuario updatedUsuario = usuarioRepository.save(usuario);

        return (perfilMapper.toDto(updatedUsuario));
    }
}
