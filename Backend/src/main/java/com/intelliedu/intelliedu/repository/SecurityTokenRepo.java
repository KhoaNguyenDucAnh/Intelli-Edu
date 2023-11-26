package com.intelliedu.intelliedu.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intelliedu.intelliedu.entity.SecurityToken;

/**
 * ActivationTokenRepo
 */
public interface SecurityTokenRepo extends JpaRepository<SecurityToken, UUID> {
	
	Optional<SecurityToken> findByToken(String token);
}
