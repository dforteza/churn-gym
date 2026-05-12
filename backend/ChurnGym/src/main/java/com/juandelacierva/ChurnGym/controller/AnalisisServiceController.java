package com.juandelacierva.ChurnGym.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juandelacierva.ChurnGym.dto.AnalisisResumenResponseDto;
import com.juandelacierva.ChurnGym.dto.ClienteAnalisisResponseDto;
import com.juandelacierva.ChurnGym.service.interfaces.AnalisisService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/analisis")
@RequiredArgsConstructor
public class AnalisisServiceController 
{
    private final AnalisisService analisisService;
    

    @GetMapping("/vigente")
    public ResponseEntity<AnalisisResumenResponseDto> getAnalisisVigente() 
    {
        return (new ResponseEntity<>(analisisService.getAnalisisVigente(), HttpStatus.OK));
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
