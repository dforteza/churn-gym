package com.juandelacierva.ChurnGym.service.interfaces;

import com.juandelacierva.ChurnGym.dto.LoginRequest;
import com.juandelacierva.ChurnGym.dto.LoginResponse;

public interface AuthService 
{
    LoginResponse login(LoginRequest request);
}
