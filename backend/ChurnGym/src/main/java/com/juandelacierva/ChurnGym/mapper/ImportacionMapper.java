package com.juandelacierva.ChurnGym.mapper;

import com.juandelacierva.ChurnGym.domain.Importacion;
import com.juandelacierva.ChurnGym.dto.ModeloResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

// GET /api/modelos
// GET /api/modelos/{id}
@Mapper(componentModel = "spring")
public interface ImportacionMapper 
{
    // Entidad -> Dto
    @Mapping(target = "totalClientes", source = "totalRegistros")
    @Mapping(target = "alto",          ignore = true)
    @Mapping(target = "medio",         ignore = true)
    @Mapping(target = "bajo",          ignore = true)
    ModeloResponseDto toResumenResponse(Importacion importacion);

    // Lista Entidades -> Lista Dtos
    List<ModeloResponseDto> toResumenResponseList(List<Importacion> importaciones);
}
