package com.intelliedu.intelliedu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intelliedu.intelliedu.entity.ActivationToken;

/**
 * ActivationTokenRepo
 */
public interface ActivationTokenRepo extends JpaRepository<ActivationToken, String> {
  
}
