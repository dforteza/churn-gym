package com.juandelacierva.ChurnGym.mapper;

import com.juandelacierva.ChurnGym.dto.AnalisisResumenResponseDto;
import com.juandelacierva.ChurnGym.dto.ClienteAnalisisResponseDto;
import com.juandelacierva.ChurnGym.domain.ClientePrivado;
import com.juandelacierva.ChurnGym.domain.NivelRiesgo;
import com.juandelacierva.ChurnGym.domain.ResultadoAnalisis;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    // METODO CUSTOM PARA MOSTRAR RESUMEN DE ANALISIS
    public AnalisisResumenResponseDto toAnalisisResumenResponse(
            List<ResultadoAnalisis> resultados, LocalDateTime calculadoEn)
    {
        int alto  = 0;
        int medio = 0;
        int bajo = 0;
        List<ClienteAnalisisResponseDto> items = new ArrayList<>();

        // 1. PARA CADA RESULTADO, CONVERTIRLO A DTO Y CONTAR LOS NIVELES DE RIESGO
        for (ResultadoAnalisis r : resultados) 
        {
            // 1.1 OBTENER LOS DATOS PRIVADOS DEL CLIENTE PARA EL MAPEADO
            // resultado -> clienteDatos -> clientePrivado (2 NIVELES DE RELACIÓN)
            ClientePrivado privado = r.getClienteDatos().getClientePrivado();

            // 1.2 RESULTADO + DATOS PRIVADOS -> CLIENTE ANALISIS RESPONSE
            items.add(toClienteAnalisisResponse(r, privado));

            // 1.3 CONTAR LOS NIVELES DE RIESGO
            if (r.getNivelRiesgo() == NivelRiesgo.ALTO)
                alto++;
            else if (r.getNivelRiesgo() == NivelRiesgo.MEDIO)
                medio++;
            else if (r.getNivelRiesgo() == NivelRiesgo.BAJO)
                bajo++;
        }

        // 2. CREAR Y DEVOLVER ANALISIS RESUMEN RESPONSE
        return (new AnalisisResumenResponseDto(calculadoEn, resultados.size(), alto, medio, bajo, items));
    }
}
