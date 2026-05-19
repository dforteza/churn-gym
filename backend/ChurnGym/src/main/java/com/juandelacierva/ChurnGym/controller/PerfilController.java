package com.juandelacierva.ChurnGym.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.juandelacierva.ChurnGym.dto.PerfilResponseDto;
import com.juandelacierva.ChurnGym.dto.PerfilUpdateDto;
import com.juandelacierva.ChurnGym.service.interfaces.PerfilService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/perfil")
public class PerfilController {

    private final PerfilService perfilService;

    @GetMapping
    public ResponseEntity<PerfilResponseDto> getPerfil(Authentication authentication)
    {
        PerfilResponseDto perfilResponseDto = perfilService.getPerfil(authentication.getName());

        return (new ResponseEntity<>(perfilResponseDto, HttpStatus.OK));
    }

    @PutMapping
    public ResponseEntity<PerfilResponseDto> updatePerfil(
            Authentication authentication,
            @Valid @RequestBody PerfilUpdateDto dto)
    {
        PerfilResponseDto perfilResponseDto = perfilService.updatePerfil(authentication.getName(), dto);
        
        return (new ResponseEntity<>(perfilResponseDto, HttpStatus.OK));
    }
}
