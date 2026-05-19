package com.juandelacierva.ChurnGym.service.interfaces;

import com.juandelacierva.ChurnGym.dto.PerfilResponseDto;
import com.juandelacierva.ChurnGym.dto.PerfilUpdateDto;

public interface PerfilService {

    PerfilResponseDto getPerfil(String username);

    PerfilResponseDto updatePerfil(String username, PerfilUpdateDto dto);
}
