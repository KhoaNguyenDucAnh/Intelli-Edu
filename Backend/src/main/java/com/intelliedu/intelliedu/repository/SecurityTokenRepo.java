package com.intelliedu.intelliedu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intelliedu.intelliedu.entity.SecurityToken;

/**
 * ActivationTokenRepo
 */
public interface SecurityTokenRepo extends JpaRepository<SecurityToken, String> {
	
	Optional<SecurityToken> findByToken(String token);
}
