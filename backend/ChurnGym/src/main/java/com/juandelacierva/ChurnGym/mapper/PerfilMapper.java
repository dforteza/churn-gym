package com.juandelacierva.ChurnGym.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "rol", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateUsuario(PerfilUpdateDto dto, @MappingTarget Usuario usuario);
}
