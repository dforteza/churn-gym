package com.juandelacierva.ChurnGym.dto;

import com.juandelacierva.ChurnGym.domain.Rol;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UsuarioResponseDto 
{
    private Long id;
    private String username;
    private String email;
    private Rol rol;
}
