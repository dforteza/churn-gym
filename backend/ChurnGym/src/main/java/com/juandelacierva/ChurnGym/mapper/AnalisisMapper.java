package com.juandelacierva.ChurnGym.mapper;

import com.juandelacierva.ChurnGym.dto.AnalisisResumenResponseDto;
import com.juandelacierva.ChurnGym.dto.ClienteAnalisisResponseDto;
import com.juandelacierva.ChurnGym.domain.ClientePrivado;
import com.juandelacierva.ChurnGym.domain.ResultadoAnalisis;
import com.juandelacierva.ChurnGym.domain.enums.NivelRiesgo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class AnalisisMapper
{
    @Mapping(source = "resultado.clienteDatos.id",                 target = "clienteId")
    @Mapping(source = "resultado.clienteDatos.frecuenciaSemanal",  target = "frecuenciaSemanal")
    @Mapping(source = "resultado.clienteDatos.semanasInactivo",    target = "semanasInactivo")
    @Mapping(source = "resultado.clienteDatos.tendenciaMensual",   target = "tendenciaMensual")
    @Mapping(source = "resultado.clienteDatos.franjaHoraria",      target = "franjaHoraria")
    @Mapping(source = "resultado.clienteDatos.deportePrincipal",   target = "deportePrincipal")
    @Mapping(source = "resultado.clienteDatos.mesesComoSocio",     target = "mesesComoSocio")
    @Mapping(source = "resultado.nivelRiesgo",                     target = "nivelRiesgo")
    @Mapping(source = "resultado.probabilidadAbandono",            target = "probabilidadAbandono")
    @Mapping(source = "resultado.grupo",                           target = "grupo")
    @Mapping(source = "privado.nombre",                            target = "nombre")
    @Mapping(source = "privado.apellidos",                         target = "apellidos")
    @Mapping(source = "privado.email",                             target = "email")
    public abstract ClienteAnalisisResponseDto toClienteAnalisisResponse(ResultadoAnalisis resultado, ClientePrivado privado);

    public AnalisisResumenResponseDto toAnalisisResumenResponse(
        List<ResultadoAnalisis> todos,
        Page<ResultadoAnalisis> pagina,
        LocalDateTime calculadoEn
    )
    {
        // Contadores globales — se calculan sobre TODOS los clientes, ignorando filtros
        int alto  = 0;
        int medio = 0;
        int bajo  = 0;

        for (ResultadoAnalisis r : todos)
        {
            if      (r.getNivelRiesgo() == NivelRiesgo.ALTO)  alto++;
            else if (r.getNivelRiesgo() == NivelRiesgo.MEDIO) medio++;
            else if (r.getNivelRiesgo() == NivelRiesgo.BAJO)  bajo++;
        }

        // Página de resultados — solo los registros filtrados y paginados
        List<ClienteAnalisisResponseDto> items = pagina.getContent()
                .stream()
                .map(r -> toClienteAnalisisResponse(r, r.getClienteDatos().getClientePrivado()))
                .toList();

        Page<ClienteAnalisisResponseDto> paginaDto =
                new PageImpl<>(items, pagina.getPageable(), pagina.getTotalElements());

        return (new AnalisisResumenResponseDto(calculadoEn, todos.size(), alto, medio, bajo, paginaDto));
    }
}
