package com.juandelacierva.ChurnGym.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juandelacierva.ChurnGym.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>
{
	Optional<Cliente>   findByEmail(String email);
	boolean             existsByEmail(String email);
}
