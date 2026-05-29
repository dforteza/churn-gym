package com.juandelacierva.ChurnGym.mapper;

import org.mapstruct.Mapper;

import com.juandelacierva.ChurnGym.dto.UsuarioResponseDto;
import com.juandelacierva.ChurnGym.domain.Usuario;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper 
{
    UsuarioResponseDto toUsuarioResponseDto(Usuario usuario);

    List<UsuarioResponseDto> toUsuarioResponseDtoList(List<Usuario> usuarios);
}
