package com.juandelacierva.ChurnGym.service;

import com.juandelacierva.ChurnGym.dto.AnalisisResumenResponseDto;
import com.juandelacierva.ChurnGym.dto.ClienteAnalisisResponseDto;
import com.juandelacierva.ChurnGym.domain.ClienteDatos;
import com.juandelacierva.ChurnGym.domain.ResultadoAnalisis;
import com.juandelacierva.ChurnGym.domain.enums.DeportePrincipal;
import com.juandelacierva.ChurnGym.domain.enums.FranjaHoraria;
import com.juandelacierva.ChurnGym.domain.enums.GrupoRiesgo;
import com.juandelacierva.ChurnGym.domain.enums.NivelRiesgo;
import com.juandelacierva.ChurnGym.exception.ResourceNotFoundException;
import com.juandelacierva.ChurnGym.mapper.AnalisisMapper;
import com.juandelacierva.ChurnGym.repository.ClienteDatosRepository;
import com.juandelacierva.ChurnGym.repository.ResultadoAnalisisRepository;
import com.juandelacierva.ChurnGym.service.interfaces.AnalisisService;
import com.juandelacierva.ChurnGym.service.interfaces.MotorRiesgoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
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
    public AnalisisResumenResponseDto getAnalisisVigente(
            NivelRiesgo nivelRiesgo, GrupoRiesgo grupo,
            FranjaHoraria franja, DeportePrincipal deporte,
            Pageable pageable)
    {
        List<ResultadoAnalisis> todos = resultadoAnalisisRepository.findAll();

        Page<ResultadoAnalisis> pagina = resultadoAnalisisRepository
                .findWithFilters(nivelRiesgo, grupo, franja, deporte, pageable);

        LocalDateTime calculadoEn = todos.isEmpty()
                ? LocalDateTime.now()
                : todos.get(0).getCalculadoEn();

        log.info("Dashboard consultado — {} resultados | filtros: riesgo={} grupo={} franja={} deporte={}",
                todos.size(), nivelRiesgo, grupo, franja, deporte);

        return (analisisMapper.toAnalisisResumenResponse(todos, pagina, calculadoEn));
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteAnalisisResponseDto getDetalleCliente(Long clienteId) 
    {
        ResultadoAnalisis resultado = resultadoAnalisisRepository
                .findByClienteDatosId(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró análisis para el cliente con id: " + clienteId));

        ClienteAnalisisResponseDto response = analisisMapper.toClienteAnalisisResponse(resultado,
                resultado.getClienteDatos().getClientePrivado());

        log.info("Detalle solicitado — cliente #{}: {} {}",
                clienteId,
                resultado.getClienteDatos().getClientePrivado().getNombre(),
                resultado.getClienteDatos().getClientePrivado().getApellidos());

        return (response);
    }

    @Override
    @Transactional
    public AnalisisResumenResponseDto lanzarAnalisis() 
    {
        log.info("Lanzando análisis de churn");
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

        long alto  = resultados.stream().filter(r -> r.getNivelRiesgo() == NivelRiesgo.ALTO).count();
        long medio = resultados.stream().filter(r -> r.getNivelRiesgo() == NivelRiesgo.MEDIO).count();
        long bajo  = resultados.stream().filter(r -> r.getNivelRiesgo() == NivelRiesgo.BAJO).count();
        log.info("Analisis completado - {} clientes: {} ALTO / {} MEDIO / {} BAJO", resultados.size(), alto, medio, bajo);

        Pageable paginaPorDefecto = PageRequest.of(0, 10, Sort.by("probabilidadAbandono").descending());
        Page<ResultadoAnalisis> pagina = resultadoAnalisisRepository
                .findWithFilters(null, null, null, null, paginaPorDefecto);

        return (analisisMapper.toAnalisisResumenResponse(resultados, pagina, ahora));
    }

}
