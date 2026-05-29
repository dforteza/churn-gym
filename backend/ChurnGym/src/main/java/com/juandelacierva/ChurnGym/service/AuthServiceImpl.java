package com.juandelacierva.ChurnGym.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.juandelacierva.ChurnGym.domain.Usuario;
import com.juandelacierva.ChurnGym.exception.ResourceNotFoundException;
import com.juandelacierva.ChurnGym.repository.UsuarioRepository;
import com.juandelacierva.ChurnGym.security.dto.LoginRequestDto;
import com.juandelacierva.ChurnGym.security.dto.LoginResponseDto;
import com.juandelacierva.ChurnGym.security.jwt.JwtGenerator;
import com.juandelacierva.ChurnGym.service.interfaces.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService
{
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator          jwtGenerator;
    private final UsuarioRepository     usuarioRepository;

    @Override
    public LoginResponseDto login(LoginRequestDto request) 
    {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtGenerator.generateToken(authentication);

        Usuario usuario = usuarioRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        log.info("Login exitoso — usuario: {} [{}]", usuario.getUsername(), usuario.getRol());

        return (new LoginResponseDto(
                        token,
                        usuario.getUsername(),
                        usuario.getRol().name()
                    ));
    }

}
