package com.juandelacierva.ChurnGym.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.juandelacierva.ChurnGym.dto.PerfilResponseDto;
import com.juandelacierva.ChurnGym.dto.PerfilUpdateDto;
import com.juandelacierva.ChurnGym.service.interfaces.PerfilService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/perfil")
public class PerfilController {

    private final PerfilService perfilService;

    @GetMapping
    public ResponseEntity<PerfilResponseDto> getPerfil(Authentication authentication) {
        return ResponseEntity.ok(perfilService.getPerfil(authentication.getName()));
    }

    @PatchMapping
    public ResponseEntity<PerfilResponseDto> updatePerfil(
            Authentication authentication,
            @RequestBody PerfilUpdateDto dto) {
        return ResponseEntity.ok(perfilService.updatePerfil(authentication.getName(), dto));
    }
}
