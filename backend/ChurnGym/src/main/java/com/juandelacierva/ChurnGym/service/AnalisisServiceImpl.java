package com.juandelacierva.ChurnGym.service;

import com.juandelacierva.ChurnGym.dto.AnalisisResumenResponse;
import com.juandelacierva.ChurnGym.dto.ClienteAnalisisResponse;
import com.juandelacierva.ChurnGym.entity.ClienteDatos;
import com.juandelacierva.ChurnGym.entity.ResultadoAnalisis;
import com.juandelacierva.ChurnGym.exception.ResourceNotFoundException;
import com.juandelacierva.ChurnGym.mapper.AnalisisMapper;
import com.juandelacierva.ChurnGym.repository.ClienteDatosRepository;
import com.juandelacierva.ChurnGym.repository.ResultadoAnalisisRepository;
import com.juandelacierva.ChurnGym.service.interfaces.AnalisisService;
import com.juandelacierva.ChurnGym.service.interfaces.MotorRiesgoService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalisisServiceImpl implements AnalisisService 
{
    private final ClienteDatosRepository      clienteDatosRepository;
    private final ResultadoAnalisisRepository resultadoAnalisisRepository;
    private final MotorRiesgoService          motorRiesgoService;
    private final AnalisisMapper              analisisMapper;

    @Override
    @Transactional(readOnly = true)
    public AnalisisResumenResponse getAnalisisVigente() 
    {
        // 1- OBTENER RESULTADOS DE ANALISIS
        List<ResultadoAnalisis> resultados = resultadoAnalisisRepository.findAll();

        // 2- FECHA DE CÁLCULO (SI NO HAY RESULTADOS, USAMOS FECHA ACTUAL)
        LocalDateTime calculadoEn = resultados.isEmpty()
                ? LocalDateTime.now()
                : resultados.get(0).getCalculadoEn();

        // 3- MAPEAR A DTO
        return (analisisMapper.toAnalisisResumenResponse(resultados, calculadoEn));
    }

    @Override
    @Transactional
    public AnalisisResumenResponse lanzarAnalisis() 
    {
        // 1- BORRAR TABLA RESULTADO_ANALISIS
        resultadoAnalisisRepository.deleteAllInBatch();

        // 2- OBTENER RESULTADOS DE ANALISIS
        List<ClienteDatos> clientes = clienteDatosRepository.findAll();

        LocalDateTime ahora = LocalDateTime.now();

        // 3- CALCULAR RESULTADOS
        List<ResultadoAnalisis> resultados = clientes
                .stream()
                .map(datos -> {
                    ResultadoAnalisis r = new ResultadoAnalisis();
                    r.setClienteDatos(datos);
                    r.setNivelRiesgo(motorRiesgoService.calcularNivel(datos));
                    r.setProbabilidadAbandono(motorRiesgoService.calcularProbabilidad(datos));
                    r.setGrupo(motorRiesgoService.asignarGrupo(datos));
                    r.setCalculadoEn(ahora);
                    return r;
                })
                .toList();
        
        // 4- GUARDAR RESULTADOS EN BD
        resultadoAnalisisRepository.saveAll(resultados);

        return (analisisMapper.toAnalisisResumenResponse(resultados, ahora));
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteAnalisisResponse getDetalleCliente(Long clienteId) 
    {
        ResultadoAnalisis resultado = resultadoAnalisisRepository
                .findByClienteDatosId(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró análisis para el cliente con id: " + clienteId));

        return (analisisMapper.toClienteAnalisisResponse(resultado, resultado.getClienteDatos().getClientePrivado()));
    }
}
