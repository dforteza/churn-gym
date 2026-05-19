package com.juandelacierva.ChurnGym.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.juandelacierva.ChurnGym.domain.Usuario;
import com.juandelacierva.ChurnGym.dto.PerfilResponseDto;
import com.juandelacierva.ChurnGym.dto.PerfilUpdateDto;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PerfilMapper {

    PerfilResponseDto toDto(Usuario usuario);

    // Solo sobreescribe los campos no nulos del DTO — comportamiento PATCH real.
    void updateUsuario(PerfilUpdateDto dto, @MappingTarget Usuario usuario);
}
