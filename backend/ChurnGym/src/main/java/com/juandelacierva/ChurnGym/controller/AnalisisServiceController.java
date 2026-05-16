package com.juandelacierva.ChurnGym.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juandelacierva.ChurnGym.domain.enums.DeportePrincipal;
import com.juandelacierva.ChurnGym.domain.enums.FranjaHoraria;
import com.juandelacierva.ChurnGym.domain.enums.GrupoRiesgo;
import com.juandelacierva.ChurnGym.domain.enums.NivelRiesgo;
import com.juandelacierva.ChurnGym.dto.AnalisisResumenResponseDto;
import com.juandelacierva.ChurnGym.dto.ClienteAnalisisResponseDto;
import com.juandelacierva.ChurnGym.service.interfaces.AnalisisService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/analisis")
@RequiredArgsConstructor
public class AnalisisServiceController
{
    private static final int    MAX_PAGE_SIZE     = 100;
    private static final int    DEFAULT_PAGE_SIZE = 10;
    private static final String DEFAULT_SORT      = "probabilidadAbandono";

    private final AnalisisService analisisService;

    @GetMapping("/vigente")
    public ResponseEntity<AnalisisResumenResponseDto> getAnalisisVigente(
            @RequestParam(required = false) NivelRiesgo      nivelRiesgo,
            @RequestParam(required = false) GrupoRiesgo      grupo,
            @RequestParam(required = false) FranjaHoraria    franjaHoraria,
            @RequestParam(required = false) DeportePrincipal deportePrincipal,
            @PageableDefault(size = DEFAULT_PAGE_SIZE, sort = DEFAULT_SORT, direction = Sort.Direction.DESC) Pageable pageable)
    {
        if (pageable.getPageSize() > MAX_PAGE_SIZE)
            pageable = PageRequest.of(pageable.getPageNumber(), MAX_PAGE_SIZE, pageable.getSort());

        return (new ResponseEntity<>(
                analisisService.getAnalisisVigente(nivelRiesgo, grupo, franjaHoraria, deportePrincipal, pageable),
                HttpStatus.OK));
    }

    @PostMapping("/lanzar")
    public ResponseEntity<AnalisisResumenResponseDto> lanzarAnalisis()
    {
        return (new ResponseEntity<>(analisisService.lanzarAnalisis(), HttpStatus.OK));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<ClienteAnalisisResponseDto> getDetalleCliente(@PathVariable Long clienteId)
    {
        return (new ResponseEntity<>(analisisService.getDetalleCliente(clienteId), HttpStatus.OK));
    }
}
