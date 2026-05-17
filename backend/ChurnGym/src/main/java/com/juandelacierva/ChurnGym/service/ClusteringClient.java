package com.juandelacierva.ChurnGym.service;

import com.juandelacierva.ChurnGym.domain.ClienteDatos;
import com.juandelacierva.ChurnGym.domain.enums.GrupoRiesgo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClusteringClient 
{
	private final RestTemplate restTemplate;

	@Value("${clustering.service.url}")
	private String clusteringUrl;

	record ClienteClusterRequest(
			Long id,
			double frecuenciaSemanal,
			int semanasInactivo,
			double tendenciaMensual,
			int mesesComoSocio) {
	}

	record GrupoResultado(Long id, String grupo) {
	}

	record ClusteringResponse(List<GrupoResultado> resultados) {
	}

	public Map<Long, GrupoRiesgo> obtenerGrupos(List<ClienteDatos> clientes) {
		List<ClienteClusterRequest> payload = clientes
				.stream()
				.map(c -> new ClienteClusterRequest(
						c.getId(),
						c.getFrecuenciaSemanal(),
						c.getSemanasInactivo(),
						c.getTendenciaMensual(),
						c.getMesesComoSocio()))
				.toList();

		try
		{
			ClusteringResponse response = restTemplate.postForObject(
					clusteringUrl + "/cluster",
					payload,
					ClusteringResponse.class);

			if (response == null || response.resultados() == null)
				return (Map.of());

			return (response.resultados()
					.stream()
					.collect(Collectors.toMap(
							GrupoResultado::id,
							r -> GrupoRiesgo.valueOf(r.grupo())
					)));

		} catch (Exception e) 
		{
			log.warn("Clustering service no disponible ({}). Usando fallback de reglas.", e.getMessage());
			return (Map.of());
		}
	}
}
