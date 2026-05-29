package com.juandelacierva.ChurnGym.service.interfaces;

import com.juandelacierva.ChurnGym.security.dto.LoginRequestDto;
import com.juandelacierva.ChurnGym.security.dto.LoginResponseDto;

public interface AuthService {

    LoginResponseDto login(LoginRequestDto request);
}
