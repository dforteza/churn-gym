package com.juandelacierva.ChurnGym.service;

import com.juandelacierva.ChurnGym.domain.ClienteDatos;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * BLOQUE 3 — Cliente HTTP hacia el microservicio Python de clustering.
 *
 * Envía todos los clientes al endpoint /cluster y devuelve un mapa
 * clienteId → grupo asignado por KMeans.
 *
 * Si el microservicio no está disponible, devuelve un mapa vacío y
 * AnalisisServiceImpl usa el fallback de reglas de MotorRiesgoServiceImpl.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ClusteringClient {

    private final RestTemplate restTemplate;

    @Value("${clustering.service.url}")
    private String clusteringUrl;

    // ---------------------------------------------------------------
    // DTOs internos (request / response hacia Python)
    // ---------------------------------------------------------------

    record ClienteClusterRequest(
            Long   id,
            double frecuenciaSemanal,
            int    semanasInactivo,
            double tendenciaMensual,
            int    mesesComoSocio) {}

    record GrupoResultado(Long id, String grupo) {}

    record ClusteringResponse(List<GrupoResultado> resultados) {}

    // ---------------------------------------------------------------
    // Método principal
    // ---------------------------------------------------------------

    /**
     * Llama al microservicio Python con la lista completa de clientes
     * y devuelve el mapa id → grupo resultante del KMeans.
     *
     * KMeans necesita todos los clientes a la vez para calcular los clusters;
     * por eso la llamada es batch y no individual.
     */
    public Map<Long, String> obtenerGrupos(List<ClienteDatos> clientes) {
        List<ClienteClusterRequest> payload = clientes.stream()
                .map(c -> new ClienteClusterRequest(
                        c.getId(),
                        c.getFrecuenciaSemanal(),
                        c.getSemanasInactivo(),
                        c.getTendenciaMensual(),
                        c.getMesesComoSocio()))
                .toList();

        try {
            ClusteringResponse response = restTemplate.postForObject(
                    clusteringUrl + "/cluster", payload, ClusteringResponse.class);

            if (response == null || response.resultados() == null) return Map.of();

            return response.resultados().stream()
                    .collect(Collectors.toMap(GrupoResultado::id, GrupoResultado::grupo));

        } catch (Exception e) {
            log.warn("Clustering service no disponible ({}). Usando fallback de reglas.", e.getMessage());
            return Map.of();
        }
    }
}
