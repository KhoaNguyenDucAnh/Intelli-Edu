package com.intelliedu.intelliedu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intelliedu.intelliedu.entity.ActivationToken;

/**
 * ActivationTokenRepo
 */
public interface ActivationTokenRepo extends JpaRepository<ActivationToken, Long> {
	
	Optional<ActivationToken> findByToken(String token);
}
