package com.juandelacierva.ChurnGym.service;

import com.juandelacierva.ChurnGym.dto.AnalisisResumenResponseDto;
import com.juandelacierva.ChurnGym.dto.ClienteAnalisisResponseDto;
import com.juandelacierva.ChurnGym.domain.ClienteDatos;
import com.juandelacierva.ChurnGym.domain.ResultadoAnalisis;
import com.juandelacierva.ChurnGym.domain.enums.GrupoRiesgo;
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
    private final ClienteDatosRepository        clienteDatosRepository;
    private final ResultadoAnalisisRepository   resultadoAnalisisRepository;
    private final MotorRiesgoService            motorRiesgoService;
    private final ClusteringClient              clusteringClient;
    private final AnalisisMapper                analisisMapper;

    @Override
    @Transactional(readOnly = true)
    public AnalisisResumenResponseDto getAnalisisVigente() {
        List<ResultadoAnalisis> resultados = resultadoAnalisisRepository.findAll();

        LocalDateTime calculadoEn = resultados.isEmpty()
                ? LocalDateTime.now()
                : resultados.get(0).getCalculadoEn();

        AnalisisResumenResponseDto response = analisisMapper.toAnalisisResumenResponse(resultados, calculadoEn);

        return (response);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteAnalisisResponseDto getDetalleCliente(Long clienteId) {
        ResultadoAnalisis resultado = resultadoAnalisisRepository
                .findByClienteDatosId(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró análisis para el cliente con id: " + clienteId));

        ClienteAnalisisResponseDto response = analisisMapper.toClienteAnalisisResponse(resultado,
                resultado.getClienteDatos().getClientePrivado());

        return (response);
    }

    @Override
    @Transactional
    public AnalisisResumenResponseDto lanzarAnalisis() {
        resultadoAnalisisRepository.deleteAllInBatch();

        List<ClienteDatos> clientes = clienteDatosRepository.findAll();
        LocalDateTime ahora = LocalDateTime.now();

        // llamada a sklearn KMeans clustering
        Map<Long, GrupoRiesgo> grupos = clusteringClient.obtenerGrupos(clientes);

        List<ResultadoAnalisis> resultados = clientes
                .stream()
                .map(cliente -> {
                    ResultadoAnalisis r = new ResultadoAnalisis();
                    r.setClienteDatos(cliente);
                    r.setNivelRiesgo(motorRiesgoService.calcularNivel(cliente));
                    r.setProbabilidadAbandono(motorRiesgoService.calcularProbabilidad(cliente));

                    GrupoRiesgo grupo = grupos.getOrDefault(cliente.getId(), motorRiesgoService.asignarGrupo(cliente));
                    r.setGrupo(grupo);
                    r.setCalculadoEn(ahora);

                    return r;
                })
                .toList();

        resultadoAnalisisRepository.saveAll(Objects.requireNonNull(resultados));

        AnalisisResumenResponseDto response = analisisMapper.toAnalisisResumenResponse(resultados, ahora);

        return (response);
    }

}
