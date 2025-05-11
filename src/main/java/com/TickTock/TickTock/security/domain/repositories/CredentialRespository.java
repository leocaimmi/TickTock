package com.TickTock.TickTock.security.domain.repositories;

import com.TickTock.TickTock.security.domain.entities.CredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CredentialRespository extends JpaRepository<CredentialEntity, Long> {
    Optional<CredentialEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
