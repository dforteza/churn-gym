package com.juandelacierva.ChurnGym.repository;

import com.juandelacierva.ChurnGym.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> 
{
    Optional<Usuario> findByUsername(String username);
}
