package com.juandelacierva.ChurnGym.mapper;

import com.juandelacierva.ChurnGym.domain.MetricaCliente;
import com.juandelacierva.ChurnGym.dto.MetricaClienteResponseDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MetricaMapper 
{
    // Entidad → Dto
    @Mapping(target = "clienteId",   source = "cliente.id")
    @Mapping(target = "nombre",      source = "cliente.nombre")
    @Mapping(target = "apellidos",   source = "cliente.apellidos")
    @Mapping(target = "email",       source = "cliente.email")
    @Mapping(target = "nivelRiesgo", source = "nivelRiesgo")
    MetricaClienteResponseDto toResponse(MetricaCliente metrica);

    // Lista de entidades → lista de responses
    List<MetricaClienteResponseDto> toResponseList(List<MetricaCliente> metricas);
}
