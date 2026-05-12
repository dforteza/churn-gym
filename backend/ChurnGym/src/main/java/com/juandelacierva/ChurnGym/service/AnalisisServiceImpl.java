package com.juandelacierva.ChurnGym.service;

import com.juandelacierva.ChurnGym.dto.AnalisisResumenResponseDto;
import com.juandelacierva.ChurnGym.dto.ClienteAnalisisResponseDto;
import com.juandelacierva.ChurnGym.domain.ClienteDatos;
import com.juandelacierva.ChurnGym.domain.ResultadoAnalisis;
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
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AnalisisServiceImpl implements AnalisisService
{
    private final ClienteDatosRepository      clienteDatosRepository;
    private final ResultadoAnalisisRepository resultadoAnalisisRepository;
    private final MotorRiesgoService          motorRiesgoService;
    private final ClusteringClient            clusteringClient;
    private final AnalisisMapper              analisisMapper;

    @Override
    @Transactional(readOnly = true)
    public AnalisisResumenResponseDto getAnalisisVigente() 
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
    public AnalisisResumenResponseDto lanzarAnalisis() 
    {
        // 1- BORRAR TABLA RESULTADO_ANALISIS
        resultadoAnalisisRepository.deleteAllInBatch();

        // 2- OBTENER CLIENTES
        List<ClienteDatos> clientes = clienteDatosRepository.findAll();

        LocalDateTime ahora = LocalDateTime.now();

        // 3- CLUSTERING BATCH: llamada al microservicio Python (KMeans)
        //    Si el servicio no está disponible, grupos queda vacío y se usa el fallback de reglas
        Map<Long, String> grupos = clusteringClient.obtenerGrupos(clientes);

        // 4- CALCULAR RESULTADOS
        List<ResultadoAnalisis> resultados = clientes
                .stream()
                .map(datos -> {
                    ResultadoAnalisis r = new ResultadoAnalisis();
                    r.setClienteDatos(datos);
                    r.setNivelRiesgo(motorRiesgoService.calcularNivel(datos));
                    r.setProbabilidadAbandono(motorRiesgoService.calcularProbabilidad(datos));
                    r.setGrupo(grupos.getOrDefault(datos.getId(), motorRiesgoService.asignarGrupo(datos)));
                    r.setCalculadoEn(ahora);
                    return r;
                })
                .toList();
        
        // 4- GUARDAR RESULTADOS EN BD
        resultadoAnalisisRepository.saveAll(Objects.requireNonNull(resultados));

        return (analisisMapper.toAnalisisResumenResponse(resultados, ahora));
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteAnalisisResponseDto getDetalleCliente(Long clienteId) 
    {
        ResultadoAnalisis resultado = resultadoAnalisisRepository
                .findByClienteDatosId(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró análisis para el cliente con id: " + clienteId));

        return (analisisMapper.toClienteAnalisisResponse(resultado, resultado.getClienteDatos().getClientePrivado()));
    }
}
